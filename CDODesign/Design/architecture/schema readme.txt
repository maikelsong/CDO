3.X.X �汾
1. ȥ�� BigTableConfig.xsd,���÷ֿ�ֱ�,��ʹ��shardJdbc
2  ȥ�� DataService.xsd �е�MongoDB��д��,MongoDBĿǰ�仯̫��,�Ѳ�����
3  ȥ�� Framewok.xsd������ʹ��redis,��������д���ֲ�ʽ��֧�ֶ����ͣ�ԭ��������memcache ̫���ˣ��Ҳ�֧�ֶ�������
4  ȥ�� TransFilter ������ ʹ��ǰ���¼��������¼���������������memache��

5  ServiceBus.xsd �� ServiceBusResource.xsd  �� PluginsConfig.xsd ��ɣ��ֲ���Ҫ��Ϊ�˲��𷽱㣬������;����仯�ķֿ���
   ServiceBusResource.xsd ��Ҫ������ ��ϵ�����ݿ�Դ
   PluginsConfig.xsd   ������ʹ�ò��������
6  ʹ�ø߰汾castor1.4.1��dbcp2.6����JDK1.8+ ����֧��



                                                                                        
                                                                                       
              ��ServiceBusResource[���ö�����ݿ�Դ]                                
             |                                            |-------- ���TransService                           |                     
             |                                            |
ServiceBus---|                  ���������ͨplugin.xml--  |                
             |                 |                          |________ ���DataService        
              ��PluginsConfig��|
                               |                          __���TransService  
                               |                         |   
                                ���������ͨplugin.xml---|
                                                         |__���DataService
