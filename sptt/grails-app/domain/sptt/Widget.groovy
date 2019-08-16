package sptt

import grails.gorm.MultiTenant

class Widget implements MultiTenant<Widget> {

  String widgetName

    static constraints = {
    }
}
