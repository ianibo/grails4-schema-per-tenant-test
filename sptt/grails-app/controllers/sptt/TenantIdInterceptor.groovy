package sptt

import javax.servlet.http.HttpServletRequest

class TenantIdInterceptor {

  int order = HIGHEST_PRECEDENCE + 100

  public TenantIdInterceptor() {
    matchAll()
  }

  boolean before() {
    // See if this request has an X-OKAPI-TENANT header
    // If so, see if we have a hibernateDatastore for that tenant yet

    // HttpServletRequest httpServletRequest = getRequest()
    String tenantId = request.getHeader('X-TENANT')?.toLowerCase()?.trim()
    if ( tenantId ) {
      request.setAttribute('gorm.tenantId',tenantId)
    }
  }

  boolean after() { 
    true 
  }

  void afterView() {
    // no-op
  }
}
