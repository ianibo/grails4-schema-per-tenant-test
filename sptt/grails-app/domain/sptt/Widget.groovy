package sptt

import grails.gorm.MultiTenant
import org.grails.datastore.gorm.GormEntity

class Widget implements GormEntity<Widget>, MultiTenant<Widget> {

  static graphql = true;

  String widgetName

    static constraints = {
    }
}
