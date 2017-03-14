## �g�p����\�[�X�A�t�@�C���̐���

�\�[�X/�t�@�C��               | ����
----------------------------- | --------------------------------------------
geode.handson.cui.P2PChat     | Embedded���[�h�`���b�g�A�v����Main�N���X�ł��B<br>���[�U�[������͂��A�W�����͂�胁�b�Z�[�W���󂯎�肽�߂̃R�[�h��������Ă��܂��B<br>":q"�Ɠ��͂��邱�ƂŏI�����܂��B
/resources/gemfire.properties | ���geode�N���X�^�[�̐ݒ�����邽�߂̃t�@�C���ł��B<br>�f�t�H���g�ł̓N���X�p�X��ɑ��݂���gemfire.properties��T���܂��B<br>-DgemfirePropertyeFile�V�X�e���v���p�e�B���w�肷�邱�ƂňӐ}�����t�@�C����ǂݍ��ނ��Ƃ��\�ł��B
/resources/cache.xml          | �L���b�V���i���[�W�����j��ݒ肷�邽�߂̃t�@�C���ł��B<br>�f�t�H���g�ł̓N���X�p�X��ɑ��݂���cache.xml��T���܂��B<br>gemfire.properties��cache-xml-file�ŕύX���邱�Ƃ��\�ł��B


## �L���b�V���̍쐬

���[�W�����փA�N�Z�X���邽�߂ɂ́A�L���b�V�����쐬����K�v������܂��B
�L���b�V���́A���ׂẴf�[�^�ɃA�N�Z�X���邽�߂̃G���g���[�|�C���g�ł��B
�L���b�V����cache.xml�̃I�u�W�F�N�g�\���ƂȂ�܂��B
Cache���쐬����ɂ�CacheFactory���g�p���܂��B

����́AP2PChat.java�̐擪�Ɏ��̂悤�ɒǉ����܂��B

``` java
Properties props = new Properties();
if (args.length > 0 && "embeddedLocator".equals(args[0])) {
	props.setProperty("start-locator", "localhost[10335]");
} else {
	props.setProperty("locators", "localhost[10335]");
}
CacheFactory factory = new CacheFactory(props);
Cache cache = factory.create();
```

CacheFactory�̃R���X�g���N�^�ɂ́AProperties���w�肷�邱�Ƃ��ł��Agemfire.properties�̒l���㏑�����邱�Ƃ��ł��܂��B
���s���ɈقȂ�ݒ�l��API�Ŏw�肷�邱�Ƃ��\�ł��B
�����1�ڂ�Java�v���Z�X�̂ݑg�ݍ��ݎ��̃��P�[�^�[���N�����邽�߁Astart-locator��localhost��10335�|�[�g���w�肵�ċN�����Ă��܂��B
2�ڈȍ~��Java�v���Z�X�N���ł�1�ڂŋN���������P�[�^�[���w�肵�ċN�����邱�Ƃ�Geode�N���X�^�[���\������܂��B


## ���[�W�����̍쐬

���Ƀ��[�W�������쐬���܂��B
�����cache.xml��ChatMessage�Ƃ������O��REPLICATE���[�W�������쐬���܂��B
ChatMessage���[�W�����̃f�[�^�C�x���g���擾���ă��b�Z�[�W��\�����邽�߁Acache-listener�ŃC�x���g�n���h���[��ݒ肵�Ă��܂��B

``` xml
<region name="ChatMessage">
  <region-attributes refid="REPLICATE">
    <cache-listener>
      <class-name>geode.handson.cui.ChatMessageListener</class-name>
    </cache-listener>
  </region-attributes>
</region>
```

cache.xml�ō쐬�������[�W������Java�A�v���P�[�V��������擾����ɂ́A���̂悤�ɂ��܂��B

``` java
Region<String, String> region = cache.getRegion("ChatMessage");
```


## �f�[�^�̓o�^

���[�W�����փf�[�^��o�^����ꍇ�͈ȉ��̂悤�ɍs���܂��B

``` java
String key = String.format("%s@%s@%d", user, LocalDateTime.now().format(formatter), messageNo++);
region.put(key, message);
```

���͂������[�U�[���A���ݓ����A���b�Z�[�W�ԍ����L�[�ɂ��āA�S�Ă̍X�V���ǉ��o�^�ƂȂ�悤�ɂ��ăf�[�^��o�^���܂��B
�i�C�x���g�n���h���ł͓o�^�݂̂̃C�x���g���������邽�߂ɍX�V�ƂȂ�Ȃ��悤�ɂ��܂��j

���[�W������java.util.Map�iConcurrentMap�j���p�����Ă��邽�߁AMap�Ɠ������o�Ńf�[�^�̑��삪�\�ł��B


## �C�x���g�n���h��

���[�W������cache-listener���ݒ肳��Ă���ꍇ�́A�f�[�^�̒ǉ��A�X�V�A�폜�A���X���s��ꂽ�ꍇ�ɁA�ݒ肳�ꂽ�C�x���g�n���h�����Ăяo����܂��B
����́AChatMessageListener.java�Œǉ��C�x���g�̂ݏE���悤�ɂ��āA�W���o�͂փL�[�ƒl���o�͂���悤�ɂ����Ă��܂��B

``` java
@Override
public void afterCreate(EntryEvent<String, String> event) {
	System.out.println(event.getKey() + " > " + event.getNewValue());
}
```
