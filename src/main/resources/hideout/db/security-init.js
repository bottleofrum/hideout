function init(host, port, database) {

  function WebAccesRule(type, path, access) {
    this.path = path;
    this.access = access;
    this.type = type;
    this.httpMethod = 'GET';
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

  mongo = new Mongo(host+':'+port);
  db = mongo.getDB(database);

  collecions = ['security-web-access','security-roles','security-groups','security-users'];

  for(var i = 0; i < collecions.length; ++i) {
    col = db.getCollection(collecions[i]);
    if(col) {
      col.drop();
    }
  }

  access = db.getCollection('security-web-access');


  access.insert(new WebAccesRule('ANT', '/favicon.ico','permitAll'));
  access.insert(new WebAccesRule('ANT', '/resources/**','permitAll'));
  access.insert(new WebAccesRule('ANT', '/','permitAll'));
  access.insert(new WebAccesRule('ANT', '/signup*','permitAll'));
  access.insert(new WebAccesRule('ANT', '/signin*','permitAll'));
  access.insert(new WebAccesRule('ANT', '/generalError','permitAll'));
  access.insert(new WebAccesRule('ANT', '/.console/**','hasRole(\'SUPERADMIN\')'));
  access.insert(new WebAccesRule('ANT', '/r/security/**','hasRole(\'SUPERADMIN\')'));
  access.insert(new WebAccesRule('ANY', '[ANY]','authenticated'));

  role = db.getCollection('security-roles');

  role.insert({role: 'SUPERADMIN'});

  group = db.getCollection('security-groups');

  superadminGroup = new Group('SUPERADMIN',['SUPERADMIN']);
  group.insert(superadminGroup);

  users = db.getCollection('security-users');

  users.insert(new User('superadmin','$2a$10$c8OZCWzExmR52I93I8XqH.FF.pXZDj/gQEslmII0gUYUUHoYrDHua', [superadminGroup]));
}