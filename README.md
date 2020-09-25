#Beüzemelés
1)A projekt csak 11-es java sdk-val müködik, ezért ha nincs letöltve akkor 
le kell szedni 11-es jdk-t,(windowson be kell állítani a PATH környezeti változót, bele kell írni a jdk bin mappájának elérését és küldje fel a tetejére) majd beállítani(IntelliJ-ben File->Projekt Structure->Project SDK)
2)Indításhoz elöször egy mvn clean install kell, majd mvn compile    
3)A tomcat bin mappájába be kell másolni disk-ben lévő allatkert-projekt.db-t, és akkor ugyanazt az adatabázist fogja használni a webes és asztali rész 
. Ha azt akarjuk, hogy amit asztalin változtatunk az a weben is megjelenjen a folytonos másolgatás helyett megadhatjuk a db_properties-ben lévő stringet abszolút útvonalként a disken belüli adatbázishoz.  
4)A webes résznél ha már más használja azt a portot, akkor Tomccat-nél->Edit Configuration, és ott lehet állítani a http portot és a jmx portot

#Hiányosság
Képmegjelenítés nincs megvalósítva.

#Programról
Bejelentkezhetsz uname:admin pw:admin, itt tudsz addolni meg deletelni 
Registrálhatsz is.