package sptt

import grails.gorm.MultiTenant
import org.grails.datastore.gorm.GormEntity

class Widget implements MultiTenant<Widget> {

  String id
  String widgetName

  public Widget() {
  }

  static constraints = {
  }

  static mapping = {
                   id column: 'wid_id', generator: 'uuid2', length:36
              version column: 'wid_version'
           widgetName column: 'wid_name'
  }

}
