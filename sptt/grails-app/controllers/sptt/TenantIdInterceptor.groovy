package sptt

import javax.servlet.http.HttpServletRequest

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.RequestAttributes

class TenantIdInterceptor {

  int order = HIGHEST_PRECEDENCE + 100

  public TenantIdInterceptor() {
    matchAll()
  }

  boolean before() {
    log.debug("TenantIdInterceptor::before ${request.getHeader('X-TENANT')}");

    // See if this request has an X-OKAPI-TENANT header
    // If so, see if we have a hibernateDatastore for that tenant yet

    // HttpServletRequest httpServletRequest = getRequest()
    String tenantId = request.getHeader('X-TENANT')?.toLowerCase()?.trim()
    if ( tenantId ) {
      log.debug("Set gorm.tenantId attribute to ${tenantId}");
      request.setAttribute('gorm.tenantId',tenantId)

       RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes()
       requestAttributes.setAttribute('gorm.tenantId',tenantId,RequestAttributes.SCOPE_SESSION);
       log.debug("check:: ${requestAttributes.getAttribute('gorm.tenantId', RequestAttributes.SCOPE_SESSION)}");
    }

    true
  }

  boolean after() { 
    true 
  }

  void afterView() {
    // no-op
  }
}
