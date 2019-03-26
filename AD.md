# Внедрение Active Directory Kerberos авторизации.

##### Допустим, что наш домен: MYDOMAIN.ORG, контроллер домена на хосте с IP `192.168.1.254/24`

##### Допустим, что наше приложение запущено на хосте с IP `192.168.1.1/24`

##### Допустим, что условный пользователь приложения сидит на хосте `192.168.1.2/24`

##### В домене должен быть включен механизм авторизации Kerberos.

## Учетная запись для приложения.
1. Создать в Active Directory учетную запись пользователя для приложения.
`First name, Last name, Display Name, Inititals` должны совпадать (например `app`).
Проставить пареметры `User cannot change password`, `Password newer expires`.
2. Включить Kerberos шифрование для пользователя. Для этого зайти в свойства пользователя и во вкладке `Account`
в разделе `Account options` включить `This account supports Kerberos AES 128 bit encryption`
и `This account supports Kerberos AES 256 bit encryption` и `Do not require Kerberos preauthentication`.
Убедиться, что переключатель `Account expires` в состоянии `Never`.
3. Создать `.keytab` файл для приложения.
Для этого запускаем командную строку на контроллере домена и выполняем команду:
`ktpass -out app.keytab -princ HTTP/app.mydomain.org@MYDOMAIN.ORG -mapUser app -mapOp set -pass app_user_password -crypto ALL -pType KRB5_NT_PRINCIPAL`.
В домашней директории пользователя, под которым выполняласть команда, появится файл `app.keytab`.
В учетной записи приложения аттрибут `userPrincipalName` станет `HTTP\app.mydomain.org@MYDOMAIN.ORG`.
Также появится аттрибут `servicePrincipalName` = `HTTP\app.mydomain.org`.
Аттрибуты рекомендуется просматривать через программу **LDAP Admin**.
4. Прописать в настройках приложения путь к файлу `app.keytab`.

## DNS.
Добавить на DNS сервер **A** запись `app.mydomain.org 192.168.1.1`.
В `Reverse Lookup Zone` добавить соответствующую **PTR** запись.
Хост с приложением должен уметь резолвить адрес контроллера домена и KRB-сервера в домене.
(На локалках это можно провернуть через `/etc/hosts`)

## Пользователи.
1. Для пользователей также должны быть включены `This account supports Kerberos AES 128 bit encryption`
и `This account supports Kerberos AES 256 bit encryption` и `Do not require Kerberos preauthentication`.
2. http://app.mydomain.org добавить в доверенные сайты (`Свойства браузера -> Безопасность -> Надежные сайты`).
3. Для надежных сайтов зайти в `Уровень безопасности для этой зоны -> Другой -> Проверка подлинности пользователя`.
Установить радиокнопку на `Автоматический вход в сеть с текущим именем пользователя и паролем`.
4. При использовании Chrome, запускать его с параметрами:
`"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --auth-server-whitelist="*.mydomain.org" --auth-negotiate-delegate-whitelist="*.mydomain.org"`
5. В Firefox зайти в `about:config` прописать параметр `network.negotiate-auth.trusted-uris=mydomain.org`
6. В теории возможна Kerberos-авторизация и на Linux-машинах.
Для этого нужно установить `sudo apt install krb5-user`, в какчестве `REALM` указать MYDOMAIN.ORG
и выполнить в консоли `kinit username@MYDOMAIN.ORG`.
> п.1-5 настраиваемы групповыми политиками, но оставим эту боль админам Active Directory.

## NGINX
#### Пример конфигурации с которой всё работает
```bash
server {
    listen 80 default_server;
    listen [::]:80 default_server;

    server_name _ app.mydomain.org;

    location / {
            proxy_pass http://localhost:8080; 
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection 'upgrade';
            proxy_set_header Host $host;
            proxy_set_header Remote_host $remote_addr;
            proxy_cache_bypass $http_upgrade;
    }
}
```
TODO: Нужно больше поисследовать и потестить, особенно в контексте HTTPS.

# Troubleshooting.

## LDAP Admin - утилита для просмотра и редактирования LDAP дерева.
##### http://www.ldapadmin.org/download/ldapadmin.html
##### Чтобы подключиться к LDAP создаем соединение:
1. `Connection mame` - придумываем то, что нравится.
2. `Host` - имя или IP контроллера домена.
3. `Base` - DC=MYDOMAIN,DC=ORG
4. Снять чекбокс с Anonymous connection.
5. `Username` - CN=Administrator,CN=Users,DC=MYDOMAIN,DC=ORG
6. `Password` - пароль администратора домена (на нашем стенде `qweqwe123$`)

## Wireshark - программа для прослушивания сетевого траффика.
##### https://www.wireshark.org/#download
1. Может случиться так, что Kerberos авторизация на нашем приложении не работает, в логах запись:
`org.ietf.jgss.GSSException: Defective token detected (Mechanism level: GSSHeader did not find the right tag)`.
Запускаем Wireshark на хосте с приложением, применяем фильтр `(ip.addr == 192.168.1.2 or ip.addr == 192.168.1.254) and (ntlmssp or kerberos or http or ldap)`
В дампе трафика **в момент воспроизведения проблемы** мы должны увидеть **KRB**-пакеты, если вместо них **NTLMSSP**,
то с вероятностью стремящейся к 100% Kerberos в домене сконфигурирован неверно или не работает, и вместо него задействуется NTLM.
Это проблема не нашего приложения, а админов Active Directory.
Для большей ясности можно запустить аналогичные дампы на контроллере домена и компьютере пользователя.
При отсутствии графического интерфейса, дамп можно собрать из консоли (например при помощи `tcpdump`),
сохранить в формате `.pcap`, а потом открыть в Wireshark.
2. Wireshark очень полезно использовать при дебаге LDAP запросов приложения.

## Remmina - программа для подключения к удаленному рабочему столу.
Программа дает возможность подключиться к Windows машинам по RDP с Linux машин.
Помимо самой программы необходимо установить плагин **Remmina-plugin-rdp** (требуется перезапуск).

