package sptt

import javax.servlet.http.HttpServletRequest

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
      request.setAttribute('gorm.tenantId',tenantId)
      log.debug("Set gorm.tenantId attribute to ${tenantId}");
    }
  }

  boolean after() { 
    true 
  }

  void afterView() {
    // no-op
  }
}
