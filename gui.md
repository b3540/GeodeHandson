## �T�v

�N���C�A���g-�T�[�o�[�^�̃`���b�g�A�v���쐬�ł́AGeode���g�p���ăN���C�A���g-�T�[�o�[�^�̃A�v���P�[�V�������쐬������@���w�т܂����B
�Ō�́ACUI��JavaFX���g�p����GUI�ɕύX���AGeode�̐ݒ��API�ōs�����@���w�т܂��B


## �g�p����\�[�X�A�t�@�C���̐���

�\�[�X/�t�@�C��               | ����
----------------------------- | --------------------------------------------
geode.handson.gui�z���̃N���X | ��ʓI��JavaFX�A�v���P�[�V�����ł��B<br>Geode�Ƃ̐ڑ�������ChatClientEndpoint�֏W�񂳂�Ă��܂��B
/resources/gemfire.properties | �i�N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���쐬�Ƌ��L�j
/resources/guiclientcache.xml | �N���C�A���g�L���b�V���i���[�W�����j��ݒ肷�邽�߂̃t�@�C���ł��B<br>���[�W������API�ō쐬���邽�ߋ�ݒ�ł��B
/resources/chat.fxml          | JavaFX�Ŏg�p����FXML�t�@�C���B
/resources/chat.css           | JavaFX�Ŏg�p����CSS�t�@�C���B


## ���P�[�^�[�A�L���b�V���T�[�o�[�̋N��

�N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���쐬�Ɠ��l�B


## ���[�W�����̍쐬

�N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���쐬�Ɠ��l�B


## �N���C�A���g�L���b�V���̍쐬

�N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���ƈႢpool�̐ݒ肪�Ȃ����߁AAPI�ō쐬����K�v������܂��B
����́AChatClientEndpoint.java�̃R���X�g���N�^�֎��̂悤�ɒǉ����܂��B

``` java
try {
	cache = ClientCacheFactory.getAnyInstance();
} catch (CacheClosedException e) {
	System.out.println("Ignore CacheClosedException");
	Properties props = new Properties();
	props.setProperty("cache-xml-file", "guiclientcache.xml");
	ClientCacheFactory factory = new ClientCacheFactory(props).addPoolLocator(host, port).setPoolSubscriptionEnabled(true);
	cache = factory.create();
}
```

ClientCacheFactory.getAnyInstance()��ClientCache�����łɑ��݂���ꍇ�́A���̃C���X�^���X���擾�ł��܂��B
����Ăяo���iClientCache���쐬����Ă��Ȃ��j�ꍇ�ɂ́ACacheClosedException���������邽�߁A�V�K�ɍ쐬����悤�ɂ��Ă��܂��B
cache.xml�̓��e��XML�\���ɂ���������API�ōs�����Ƃ��\�ł��B


## �N���C�A���g���[�W�����̍쐬

�������N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���ƈႢcache.xml�֐ݒ肪�Ȃ����߁AAPI�ō쐬����K�v������܂��B
����́AChatClientEndpoint.java��addMessageHandler���\�b�h�֎��̂悤�ɒǉ����܂��B

``` java
Region<String, String> region = cache.getRegion(REGION_NAME);
if (region == null) {
	ClientRegionFactory<String, String> rgnFac = cache.createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY);
	region = rgnFac.addCacheListener(new ChatMessageListener()).create(REGION_NAME);
	region.registerInterest("ALL_KEYS");
}
```

���[�W���������ɂ͏������_�Őݒ肵�A����ȍ~�ύX���o���Ȃ����̂ƁA�ύX���o������̂�2��ނ�����܂��B
�Ⴆ�΁ACacheListener�͔C�ӂ̃^�C�~���O�Œǉ��A�폜���邱�Ƃ��\�ł��B
�ύX���o������̂�region.getAttributesMutator()���g�p���ĕύX���邱�Ƃ��\�ł��B


## �f�[�^�̓o�^

�N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���쐬�Ɠ��l�B
����́AChatClientEndpoint.java��sendMessage���\�b�h�Ŏ��s���Ă���B


## �C�x���g�n���h��

�N���C�A���g-�T�[�o�[�^��CUI�`���b�g�A�v���쐬�Ɠ��l�B
����́A�擾�����C�x���g��JavaFX�֓n�����߂ɍ��܂łƂ͕ʂ̎�����ChatClientEndpoint����ChatMessageListener�ōs���Ă���B
