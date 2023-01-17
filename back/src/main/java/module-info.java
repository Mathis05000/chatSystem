module back {
    exports metiers;
    exports models;
    exports session;
    exports udp;
    exports tcp;
    exports factory;
    exports db;

    requires commun;
    requires java.sql;
    requires spring.context;
}