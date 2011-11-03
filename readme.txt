NAME
    Monitis Java SDK - API Java interface for Monitis, all-in-one IT monitoring platform (http://www.monitis.com)

USAGE
	To use Monitis API you need to provide user's aikey and secretkey which can be obtained from Monitis dashboard.
	To use this SDK you can either paste apikey and secretkey in conf.properties file or provide those when instantiating Monitis object.
	So if you want the sdk to use apikey/secret key from conf.properties file then you should use this code
		Monitis monitis = new Monitis();
	If you want to ptovide different apikey/secetkey each time you use Monitis api, you should use the code below
		Monitis monitis = new Monitis(apikey, secretkey);     

	There are several global classes which allow you to access data from api, such as ExternalMonitor, TransactionMonitor, NotificationRule, etc.
	You can obtain an instance of those classes by calling getInstanceOf method of Monitis class.
	e.g.
	
	ExternalMonitor externalMonitor = (ExternalMonitor)new Monitis().getInstanceOf(APIObjectType.externalMonitor);
	
	or you can instantiate the ExternalMonitor class yourself
	
	ExternalMonitor externalMonitor =  new ExternalMonitor(); // apikey/secretkey are taken from conf.properties
	
	or 
	
	ExternalMonitor externalMonitor =  new ExternalMonitor(apikey, secretkey);
	
	
AUTHOR
    Nare Gasparyan <nare@monitis.com>

COPYRIGHT AND LICENSE

Copyright (C) 2006-2011, Monitis Inc.

This library is free software; you can redistribute it and/or modify
it under the same terms as Java itself.

About Monitis

Monitis is an all-in-one hosted systems monitoring platform.
It provides flexible API to extend its core functionality for monitoring anything,
from anywhere and at anytime.

More about Monitis API - http://monitis.com/api/api.html