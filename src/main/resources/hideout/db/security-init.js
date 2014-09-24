function init(host, port, database) {

    function WebAccesRule(type, path, access, order) {
        this.path = path;
        this.access = access;
        this.type = type;
        this.httpMethod = 'GET';
        this.order = order;
    }

    function Group(name, authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    function User(username, password, groups) {
        this.username = username;
        this.password = password;
        this.groups = groups;
        this.accountCreationDate = new Date();
        this.locked = false;
        this.enabled = true;
    }

    mongo = new Mongo();
    db = mongo.getDB(database);

    collecions = ['security-web-access', 'security-roles', 'security-groups', 'security-users'];

    for (var i = 0; i < collecions.length; ++i) {
        col = db.getCollection(collecions[i]);
        if (col) {
            col.drop();
        }
    }

    access = db.getCollection('security-web-access');


    access.insert(new WebAccesRule('ANT', '/favicon.ico', 'permitAll', 1));
    access.insert(new WebAccesRule('ANT', '/resources/**', 'permitAll', 2));
    access.insert(new WebAccesRule('ANT', '/', 'permitAll', 3));
    access.insert(new WebAccesRule('ANT', '/signup*', 'permitAll', 4));
    access.insert(new WebAccesRule('ANT', '/signin*', 'permitAll', 5));
    access.insert(new WebAccesRule('ANT', '/generalError', 'permitAll', 6));
    access.insert(new WebAccesRule('ANT', '/.console/**', 'hasRole(\'SUPERADMIN\')', 7));
    access.insert(new WebAccesRule('ANT', '/r/security/**', 'hasRole(\'SUPERADMIN\')', 8));
    access.insert(new WebAccesRule('ANY', '[ANY]', 'authenticated', 999999));

    role = db.getCollection('security-roles');

    role.insert({authority: 'SUPERADMIN'});

    group = db.getCollection('security-groups');

    superadminGroup = new Group('SUPERADMIN', ['SUPERADMIN']);
    group.insert(superadminGroup);

    users = db.getCollection('security-users');

    users.insert(new User('superadmin', '$2a$10$c8OZCWzExmR52I93I8XqH.FF.pXZDj/gQEslmII0gUYUUHoYrDHua', [superadminGroup]));
}