const ability = require("other/ability");
const c1 = Color.valueOf("84f491");
var spawnTime = 60 * 24;
const la = extend(LaserBulletType, {});
la.length = 200;
la.width = 20;
la.lifetime = 20;
la.damage = 88;
la.recoil = 0;
la.sideAngle = 2;
la.sideWidth = 2;
la.sideLength = 70;
la.healPercent = 10;
la.collidesTeam = true;
la.colors = [c1, c1, Color.white];

const tera = extendContent(UnitType, 'Tera', {});
tera.abilities.add(ability.MendFieldAbility(150, 150, 12));
tera.abilities.add(new UnitSpawnAbility(UnitTypes.poly, spawnTime, 31.5, -27.5), new UnitSpawnAbility(UnitTypes.poly, spawnTime, -31.5, -27.5));
tera.abilities.add(new ForceFieldAbility(140, 5, 8500, 60 * 8), new RepairFieldAbility(200, 60 * 2, 140));
tera.constructor = prov(() => extend(UnitTypes.mega.constructor.get().class, {}));
tera.weapons.add(
    (() => {
        const w = new Weapon("btm-Tera-weapon");
        w.shake = 4;
        w.shootY = 2;
        w.reload = 60;
        w.bullet = la;
        w.rotate = true;
        w.rotateSpeed = 2;
        w.shootSound = Sounds.laser;
        w.x = 25;
        w.y = -6;
        return w;
    })()
);
tera.armor = 17;
tera.flying = true;
tera.speed = 0.7;
tera.hitSize = 66;
tera.accel = 0.04;
tera.rotateSpeed = 1;
tera.baseRotateSpeed = 20;
tera.drag = 0.018;
tera.shake = 3;
tera.health = 62000;
tera.mineSpeed = 7;
tera.mineTier = 10;
tera.buildSpeed = 8;
tera.itemCapacity = 600;
tera.engineOffset = 36;
tera.engineSize = 10;
tera.rotateShooting = true;
tera.drawShields = false;
tera.lowAltitude = true;
tera.payloadCapacity = (6 * 6) * Vars.tilePayload;
tera.ammoCapacity = 1500;
tera.ammoResupplyAmount = 20;
tera.ammoType = AmmoTypes.power;
tera.commandLimit = 8;

exports.tera = tera;