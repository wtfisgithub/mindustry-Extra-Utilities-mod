package ExtraUtilities.worlds.entity.bullet;

import ExtraUtilities.worlds.blocks.turret.Stinger.*;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Unit;

public class DeathLaser extends LaserBulletType {
    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        super.hitEntity(b, entity, health);
        if(entity instanceof Unit){
            if(!(b.owner instanceof StingerBuild)) return;
            StingerBuild owner = (StingerBuild) b.owner;

            if(!owner.unitMap.containsKey((Unit) entity)) owner.unitMap.put((Unit) entity, 0f);
        }
    }
}
