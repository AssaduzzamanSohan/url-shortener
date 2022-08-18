Ext.define('Admin.view.home.Home', {
	extend: 'Ext.panel.Panel',
	xtype: 'home',

	requires: [
		'Ext.grid.*',
		'Ext.toolbar.Paging',
		'Ext.layout.container.Border'
	],

	controller: 'allController',
	viewModel: {
		type: 'home'
	},

	layout: 'border',
    width: 0.80 * (window.innerWidth),
    height: 0.90 * (window.innerHeight),
    cls: Ext.baseCSSPrefix + 'shadow',

    bodyBorder: false,

    defaults: {
        collapsible: true,
        split: true,
        bodyPadding: 10
    },

	items: [
		{
			title: 'Shorten Your URL',
			
			collapsible: false,
            region: 'center',
            margin: '5 0 0 0',
			xtype: 'panel',
			reference: 'shortenUrlPanel',
			height: window.innerHeight,
			bodyPadding: 5,
			border: 'true',
			layout: 'column',
    		columnWidth: 1,
			items: [
				{
					xtype: 'textfield',
					reference: 'longUrl',
					fieldLabel: 'Long Url',
					emptyText: 'Past your Long URL',
					labelWidth: 60,
					columnWidth: 1,
					margin : '0 0 0 5',
				},
				{
					xtype: 'button',
					padding: 2,
					margin : '20 0 0 5',
					text: 'Short it',
					reference: 'doShort',
					columnWidth: .1,
					listeners: {
						click: 'doShort'
					}
				},
				{
                    xtype: 'displayfield',
                    columnWidth: .9,
                    margin : '0 0 0 10',
                    hidden: true
                },
				{
					xtype: 'textfield',
					reference: 'shortUrl',
					fieldLabel: 'Short Url',
					editable: false,
					labelWidth: 60,
					columnWidth: 1,
					margin : '20 0 0 5'
				}
			]
		}
	]
});
