Ext.define('Admin.Application', {
    extend: 'Ext.app.Application',
    
    name: 'Admin',

    stores: [
    ],

    defaultToken : 'home',

    // The name of the initial view to create. This class will gain a "viewport" plugin
    // if it does not extend Ext.Viewport.
    //
    mainView: 'Admin.view.home.Home',

    launch: function () {
        
        Ext.require('Ext.form.field.Display');
        Ext.require('Ext.form.FieldContainer');
        Ext.require('Ext.layout.container.Form');
        
        Ext.Ajax.setTimeout(1800000);
        
        Ext.ariaWarn = Ext.emptyFn;
        this.redirectTo("home");
    }
});
