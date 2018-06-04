package cn.luliangwei.mss.api.app.filters;

import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 
 * 网关限流过滤器.
 * </p>
 * 1、单节点下的限流. 
 * 2、分布式环境下的限流.
 * 3、微服务粒度的限流.
 *
 * @author luliangwei
 * @since 0.0.1
 */
@Component
public class RateLimitZuulFilter extends ZuulFilter {

    @Autowired
    private final RateLimiter rateLimiter = RateLimiter.create(1000.0);
    
    private Map<String, RateLimiter> map = Maps.newConcurrentMap();

    @Override
    public boolean shouldFilter() {
        // 这里可以加一个限流开启的开关,开启限流返回true,关闭则返回false
        // 可以在配置文件里配置,也可以根据一定的条件触发来配置,比如满足条件返回true,否则返回false
        return true;
    }
    @Override
    public int filterOrder() {
        //注：这个说明针对于 < 微服务粒度的限流  > 这种情况
        // 这边的order一定要大于org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter的order
        // 也就是要大于5
        // 否则，RequestContext.getCurrentContext()里拿不到serviceId等数据。
        return FilterConstants.SEND_FORWARD_FILTER_ORDER;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public Object run() throws ZuulException {
        //1、单个节点的限流方案
        try {
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();
            
            //一下片段是针对于服务级别的限流,需配置路由规则
            // ---------------start----------
            
            // 对于service格式的路由，走RibbonRoutingFilter
            String key = null;
            String serviceId = (String) currentContext.get("serviceId"); //get(Object key) 这个key需要确认是什么
            if(serviceId != null) {
                key = serviceId;
                map.putIfAbsent(serviceId, RateLimiter.create(1000.0));
            }
            // 如果压根不走RibbonRoutingFilter，则认为是URL格式的路由
            else {
                // 对于URL格式的路由，走SimpleHostRoutingFilter
                URL routeHost = currentContext.getRouteHost();
                if (routeHost != null) {
                    String url = routeHost.toString();
                    key = url;
                    map.putIfAbsent(url, RateLimiter.create(2000.0));
                }
            }
            RateLimiter rateLimiter = map.get(key);
            // ---------------end----------
            
            if (!rateLimiter.tryAcquire()) {
                HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
                response.setContentType(MediaType.TEXT_PLAIN_VALUE);
                response.setStatus(httpStatus.value());
                response.getWriter().append(httpStatus.getReasonPhrase());

                currentContext.setSendZuulResponse(false);

                throw new ZuulException(httpStatus.getReasonPhrase(), 
                                        httpStatus.value(), 
                                        httpStatus.getReasonPhrase());
            }
        } catch (Throwable e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
