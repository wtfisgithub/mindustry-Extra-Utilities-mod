package ExtraUtilities.content;

import ExtraUtilities.worlds.blocks.distribution.PhaseNode;
import ExtraUtilities.worlds.blocks.distribution.StackHelper;
import ExtraUtilities.worlds.blocks.effect.Breaker;
import ExtraUtilities.worlds.blocks.effect.CoreKeeper;
import ExtraUtilities.worlds.blocks.fireWork;
import ExtraUtilities.worlds.blocks.heat.*;
import ExtraUtilities.worlds.blocks.liquid.LiquidUnloadingValve;
import ExtraUtilities.worlds.blocks.liquid.SortLiquidRouter;
import ExtraUtilities.worlds.blocks.power.LightenGenerator;
import ExtraUtilities.worlds.blocks.power.SpaceGenerator;
import ExtraUtilities.worlds.blocks.power.ThermalReactor;
import ExtraUtilities.worlds.blocks.production.*;
import ExtraUtilities.worlds.blocks.turret.*;
import ExtraUtilities.worlds.blocks.turret.TowerDefence.CrystalTower;
import ExtraUtilities.worlds.blocks.turret.wall.Domain;
import ExtraUtilities.worlds.blocks.unit.ADCPayloadSource;
import ExtraUtilities.worlds.drawer.*;
import ExtraUtilities.worlds.entity.bullet.*;
import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.DirectionLiquidBridge;
import mindustry.world.blocks.distribution.DuctBridge;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.consumers.ConsumeLiquidFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.*;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.*;
import static mindustry.content.Fx.*;
import static mindustry.type.ItemStack.*;
import static ExtraUtilities.ExtraUtilitiesMod.*;

public class EUBlocks {
    public static Block
        //drill?
            arkyciteExtractor, nitrogenWell, quantumExplosion, minerPoint, minerCenter,
        //liquid
            ekPump, liquidSorter, liquidValve, communicatingValve, liquidIncinerator,
        //transport
            stackHelper, itemNode, liquidNode, reinforcedDuctBridge, phaseReinforcedBridgeConduit, ekMessDriver,
        //production
            T2blast, T2sporePress, stoneExtractor, stoneCrusher, stoneMelting, T2oxide, cyanogenPyrolysis,
        /** 光束合金到此一游*/
            LA, ELA,
        //heat
            thermalHeater, ventHeater, largeElectricHeater, slagReheater, heatTransfer, heatDistributor, heatDriver,
        //power
            liquidConsumeGenerator, thermalReactor, LG, heatPower, windPower, waterPower,
        //turret
            dissipation, guiY, javelin, onyxBlaster, celebration, celebrationMk2, sancta, RG, fiammetta, turretResupplyPoint,
        //unit
            imaginaryReconstructor, finalF,
        //other&sandbox
            coreKeeper, quantumDomain, breaker,
            randomer, fireWork, allNode, ADC, guiYsDomain, crystalTower;
    public static void load(){
        arkyciteExtractor = new AttributeCrafter("arkycite-extractor"){{
            requirements(Category.production, with(Items.carbide, 35, Items.oxide, 50, Items.thorium, 150, Items.tungsten, 100));
            consumePower(8f);
            consumeLiquid(Liquids.nitrogen, 4/60f);
            consumeItem(Items.oxide);
            baseEfficiency = 0.5f;
            attribute = EUAttribute.EKOil;
            maxBoost = 1.5f;

            hasPower = hasItems = hasLiquids = true;

            craftTime = 60 * 2f;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(Liquids.arkycite), new DrawDefault(), new DrawRegion("-top"));
            outputLiquid = new LiquidStack(Liquids.arkycite, 1);
            liquidCapacity = 120;
            size = 3;
        }};
//        nitrogenWell = new ThermalGenerator("nitrogen-well"){{
//            requirements(Category.production, with(Items.graphite, 100, Items.silicon, 120, Items.tungsten, 80, Items.oxide, 100));
//            attribute = Attribute.steam;
//            group = BlockGroup.liquids;
//            displayEfficiencyScale = 1f / 9f;
//            minEfficiency = 9f - 0.0001f;
//            powerProduction = 90f/60f/9f;
//            displayEfficiency = false;
//            generateEffect = Fx.turbinegenerate;
//            effectChance = 0.04f;
//            size = 3;
//            ambientSound = Sounds.hum;
//            ambientSoundVolume = 0.06f;
//
//            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.5f * 9f){{
//                blurThresh = 0.01f;
//            }});
//
//            hasLiquids = true;
//            outputLiquid = new LiquidStack(Liquids.nitrogen, (8f - (hardMod ? 2 : 0)) / 60f/ 9);
//            liquidCapacity = 20f;
//            fogRadius = 3;
//        }};
        nitrogenWell = new AttributeCrafter("nitrogen-well"){{
            requirements(Category.production, with(Items.graphite, 100, Items.silicon, 120, Items.tungsten, 80, Items.oxide, 100));
            attribute = Attribute.steam;
            group = BlockGroup.liquids;
            minEfficiency = 9f - 0.0001f;
            baseEfficiency = 0f;
            displayEfficiency = false;
            craftEffect = Fx.turbinegenerate;
            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.5f * 9f){{
                blurThresh = 0.01f;
            }});
            craftTime = 120f;
            size = 3;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            hasLiquids = true;
            boostScale = 1f / 9f;
            outputLiquid = new LiquidStack(Liquids.nitrogen, 10f / 60f);
            consumePower(40/60f);
            liquidCapacity = 30f;
            fogRadius = 3;
        }};
        quantumExplosion = new ExplodeDrill("quantum-explosion"){{
            if(!hardMod) {
                requirements(Category.production, with(Items.thorium, 600, Items.silicon, 800, Items.phaseFabric, 200, Items.surgeAlloy, 200));
                drillTime = 60f * 3f;
            } else {
                requirements(Category.production, with(Items.thorium, 800, Items.silicon, 1000, Items.phaseFabric, 240, Items.surgeAlloy, 260));
                drillTime = 60 * 4f;
                drillTimeBurst = 60f * 5.5f;
            }
            size = 5;
            drillMultipliers.put(Items.beryllium, 2f);
            drillMultipliers.put(Items.sand, 3f);
            drillMultipliers.put(Items.scrap, 3f);
//            for(Item i : Vars.content.items()){
//                if(i.hardness <= 2){
//                    drillMultipliers.put(i, 3-i.hardness);
//                }
//            }
            coolant = consumeCoolant(0.3f);
            tier = Integer.MAX_VALUE;
            //consumeLiquid(Liquids.water, 0.2f);
            itemCapacity = 100;
            hasPower = true;
            consumePower(160f / 60f);

            updateEffect = Fx.none;
            drillEffect = Fx.none;
            shake = 4;
            circleRange = 5;
            drawRim = true;


            alwaysUnlocked = true;
        }};
        minerPoint = new MinerPoint("miner-point"){{
            requirements(Category.production, with(Items.beryllium, 120, Items.graphite, 120, Items.silicon, 85, Items.tungsten, 50));
            consumePower(2);
            consumeLiquid(Liquids.ozone, 6/60f);

            blockedItem = Items.thorium;
            droneConstructTime = 60 * 10f;
            tier = 5 - (hardMod ? 1 : 0);
            //alwaysUnlocked = true;
        }};
        minerCenter = new MinerPoint("miner-center"){{
            requirements(Category.production, with(Items.tungsten, 360, Items.oxide, 125, Items.carbide, 120, Items.surgeAlloy, 130));
            consumePower(3);
            consumeLiquid(Liquids.cyanogen, 3/60f);

            range = 18;
            alwaysCons = true;
            //blockedItem = Items.thorium;
            dronesCreated = 6;
            droneConstructTime = 60 * 7f;
            tier = 7 - (hardMod ? 1 : 0);
            size = 4;
            itemCapacity = 300;

            MinerUnit = EUUnitTypes.T2miner;

            //alwaysUnlocked = true;
        }};


        ekPump = new Pump("chemical-combustion-pump"){{
            requirements(Category.liquid, with(Items.tungsten, 80, Items.silicon, 80, Items.oxide, 60, Items.carbide, 30, Items.surgeAlloy, 50));
            consumeLiquid(Liquids.cyanogen, 1f / 60f);

            pumpAmount = 240f / 60f / 9f;
            liquidCapacity = 240f;
            size = 3;
        }};
        liquidSorter = new SortLiquidRouter("liquid-sorter"){{
            requirements(Category.liquid, with(Items.silicon, 8, Items.beryllium, 4));
            liquidCapacity = 30f;
            liquidPadding = 3f/4f;
            researchCostMultiplier = 3;
            underBullets = true;
            rotate = false;

            //alwaysUnlocked = true;
        }};
        liquidValve = new SortLiquidRouter("liquid-valve"){{
            requirements(Category.liquid, with(Items.graphite, 6, Items.beryllium, 6));
            liquidCapacity = 30f;
            liquidPadding = 3f/4f;
            researchCostMultiplier = 3;
            underBullets = true;
            configurable = false;

            //alwaysUnlocked = true;
        }};
        communicatingValve = new LiquidUnloadingValve("communicating-valve"){{
            requirements(Category.liquid, with(Items.silicon, 20, Items.oxide, 25, Items.graphite, 30));
            health = 80;
        }};

        liquidIncinerator = new LiquidIncinerator("liquid-incinerator"){{
            requirements(Category.crafting, with(Items.oxide, 8, Items.silicon, 5));
            consumePower(0.9f);
            hasLiquids = true;
            size = 1;
        }};


        stackHelper = new StackHelper("stack-helper"){{
            requirements(Category.distribution, with(Items.silicon, 20, Items.phaseFabric, 10, Items.plastanium, 20));
            size = 1;
            health = 60;
            buildCostMultiplier = 0.6f;
        }};
        itemNode = new PhaseNode("i-node"){{
            requirements(Category.distribution, with(Items.copper, 110, Items.lead, 80, Items.silicon, 100, Items.graphite, 85, Items.titanium, 45, Items.thorium, 40, Items.phaseFabric, 18));
            buildCostMultiplier = 0.25f;
            range = 25 - (hardMod ? 5 : 0);
            hasPower = true;
            envEnabled |= Env.space;
            consumePower(1f);
            transportTime = 1f;

            placeableLiquid = true;
        }};
        liquidNode = new PhaseNode("lb"){{
            requirements(Category.liquid, with(Items.metaglass, 80, Items.silicon, 90, Items.graphite, 85, Items.titanium, 45, Items.thorium, 40, Items.phaseFabric, 25));
            buildCostMultiplier = 0.25f;
            range = 25 - (hardMod ? 5 : 0);
            hasPower = true;
            canOverdrive = false;
            hasLiquids = true;
            hasItems = false;
            outputsLiquid = true;
            consumePower(1f);

            placeableLiquid = true;
            //transportTime = 1;
        }};

        reinforcedDuctBridge = new DuctBridge("reinforced-duct-bridge"){{
            requirements(Category.distribution, with(Items.beryllium, 15, Items.tungsten, 15, Items.graphite, 10));
            speed = 4f;
            buildCostMultiplier = 1.5f;
            itemCapacity = 5;
            range = 6;
            researchCostMultiplier = 0.3f;
            health = 150;
        }};
        phaseReinforcedBridgeConduit = new DirectionLiquidBridge("phase-reinforced-bridge-conduit"){{
            requirements(Category.liquid, with(Items.graphite, 15, Items.beryllium, 15, Items.phaseFabric, 10));
            range = 7;
            hasPower = false;
            researchCostMultiplier = 0.5f;
            underBullets = true;
            floating = true;
            placeableLiquid = true;
            Block p = Vars.content.block("extra-utilities-conduit");
            if(p != null) ((ArmoredConduit)p).rotBridgeReplacement = this;

            health = 120;
        }};
        ekMessDriver = new MassDriver("Ek-md"){{
            requirements(Category.distribution, with(Items.silicon, 75, Items.tungsten, 100, Items.thorium, 55, Items.oxide, 45));
            size = 2;
            itemCapacity = 50;
            reload = 200f;
            range = 37.5f * 8;
            rotateSpeed = 0.6f;
            consumePower(1.4f);
            bullet = new MassDriverBolt(){{
                hittable = false;
                absorbable = false;
                collides = false;
                collidesAir = false;
                collidesGround = false;
            }
                @Override
                public void draw(Bullet b){
                    float w = 7f, h = 9f;

                    Draw.color(Pal.bulletYellowBack);
                    Draw.rect("shell-back", b.x, b.y, w, h, b.rotation() + 90);

                    Draw.color(Pal.bulletYellow);
                    Draw.rect("shell", b.x, b.y, w, h, b.rotation() + 90);

                    Draw.reset();
                }
            };
        }};


        T2blast = new GenericCrafter("T2-blast"){{
            requirements(Category.crafting, with(Items.lead, 120, EUItems.crispSteel, 100, Items.silicon, 160, Items.thorium, 90));
            hasItems = true;
            hasLiquids = true;
            itemCapacity = 12;
            liquidCapacity = 40;
            hasPower = true;
            outputItems = new ItemStack[]{new ItemStack(Items.blastCompound, 4), new ItemStack(Items.scrap, 1)};
            size = 3;
            envEnabled |= Env.space;
            craftTime = 120f;

            drawer = new DrawMulti(new DrawDefault(), new DrawLiquidRegion(Liquids.water));

            updateEffect = Fx.wet;
            updateEffectChance = 0.1f;
            craftEffect = EUFx.diffuse(size, Items.blastCompound.color, 20);

            consumeItems(with(Items.pyratite, 3, Items.coal, 1));
            consumeLiquid(Liquids.water, 20f/60);
            consumePower(50f/60);
        }};
        T2sporePress = new GenericCrafter("T2-spore-press"){{
            requirements(Category.crafting, with(Items.plastanium, 60, Items.silicon, 120, EUItems.crispSteel, 45));
            liquidCapacity = 60f;
            craftTime = 30f;
            outputLiquid = new LiquidStack(Liquids.oil, 1);
            size = 3;
            scaledHealth = 60;
            hasLiquids = true;
            hasPower = true;
            craftEffect = new Effect(23, e -> {
                float scl = Math.max(e.rotation, 1);
                color(Tmp.c1.set(Pal.gray).mul(1.1f), Items.sporePod.color, e.fin());
                randLenVectors(e.id, 8, size * 8f + 4 * e.finpow() * scl, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, e.fout() * 3.5f * scl + 0.3f);
                });
            }).layer(Layer.debris);
            updateEffect = sporeSlowed;
            drawer = new DrawMulti(new DrawDefault(), new DrawFrames(), new DrawLiquidRegion());

            consumeItem(Items.sporePod, 3);
            consumePower(1.5f);
        }};

        //stone!!!
        stoneExtractor = new AttributeCrafter("stoneExtractor"){{
            requirements(Category.production, with(Items.silicon, 50, Items.graphite, 50));
            outputItem = new ItemStack(EUItems.stone, 1);
            craftTime = 90;
            size = 2;
            hasPower = true;
            attribute = EUAttribute.stone;
            baseEfficiency = 0;
            minEfficiency = 0.001f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-rot", -4){{
                        x = -2.8f;
                        y = 2.8f;
                    }},
                    new DrawRegion("-rot", 4){{
                        x = 2.8f;
                        y = 2.8f;
                    }},
                    new DrawRegion("-rot", -4){{
                        x = 2.8f;
                        y = -2.8f;
                    }},
                    new DrawRegion("-rot", 4){{
                        x = -2.8f;
                        y = -2.8f;
                    }},
                    new DrawDefault()
            );

            craftEffect = Fx.smokeCloud;
            updateEffect = new Effect(20, e -> {
                color(Pal.gray, Color.lightGray, e.fin());
                randLenVectors(e.id, 6, 3f + e.fin() * 6f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fout() * 2f, 45);
                });
            });

            consumePower(1f);
        }};
        stoneCrusher = new GenericCrafter("stoneCrusher"){{
            requirements(Category.crafting, with(Items.silicon, 55, Items.thorium, 40));
            consumeItem(EUItems.stone, 2);
            outputItems = ItemStack.with(Items.sand, 1, Items.scrap, 2);
            craftTime = 60;
            size = 2;
            hasPower = hasItems = true;
            consumePower(1.5f);
            craftEffect = new Effect(23, e -> {
                float scl = Math.max(e.rotation, 1);
                color(Tmp.c1.set(Pal.gray).mul(1.1f), Items.sand.color, e.fin());
                randLenVectors(e.id, 8, 19f * e.finpow() * scl, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, e.fout() * 3.5f * scl + 0.3f);
                });
            }).layer(Layer.debris);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawFrames(){{frames = 5;}}, new DrawDefault(), new DrawRegion("-top"));
        }};
        stoneMelting = new HeatCrafter("stoneMelting"){{
            requirements(Category.crafting, with(Items.silicon, 180, EUItems.stone, 100, Items.graphite, 80, Items.oxide, 40));
            size = 3;
            consumeItem(EUItems.stone);
            heatRequirement = 6;
            outputLiquid = new LiquidStack(Liquids.slag, 20f/60f);
            hasItems = hasLiquids = true;
            hasPower = false;
            craftTime = 30f;
            liquidCapacity = 120;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(), new DrawDefault(), new DrawHeatInput());
        }};

        T2oxide = new HeatProducer("T2oxide"){{
            requirements(Category.crafting, with(Items.oxide, 150, Items.graphite, 300, Items.silicon, 300, Items.carbide, 110, Items.thorium, 100));
            size = 5;
            hasLiquids = true;
            canOverdrive = true;

            outputItem = new ItemStack(Items.oxide, 10);

            consumeLiquids(LiquidStack.with(Liquids.ozone, 4f / 60f, Liquids.nitrogen, 4f / 60f));
            //consumeLiquid(Liquids.ozone, 4f / 60f);
            consumeItems(with(Items.beryllium, 10));
            consumePower(270f/60f);

            rotateDraw = false;
            craftEffect = Fx.drillSteam;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(Liquids.ozone), new DrawDefault(), new DrawRegion("-top"), new DrawHeatOutput());

            regionRotated1 = 2;
            craftTime = 60f * 4f;
            liquidCapacity = 50f;
            itemCapacity = 80;
            heatOutput = 25f;
        }};
        cyanogenPyrolysis = new HeatCrafter("cyanogen-pyrolysis"){{
            requirements(Category.crafting, with(Items.thorium, 100, Items.silicon, 150, Items.tungsten, 100, Items.oxide, 50, Items.carbide, 20));
            size = 3;

            drawer = new DrawMulti(new DrawDefault(), new DrawHeatInput());

            ambientSound = Sounds.fire;
            ambientSoundVolume = 0.02f;

            hasLiquids = true;
            liquidCapacity = 80f;
            consumePower(3f);
            heatRequirement = 8f;
            consumeLiquid(Liquids.arkycite, 1f);
            outputLiquid = new LiquidStack(Liquids.cyanogen, 3f/60f);

            maxEfficiency = 4;
            craftTime = 4 * 60f;
        }};

        LA = new GenericCrafter("LA"){{
            requirements(Category.crafting, with(Items.silicon, 135, Items.lead, 200, Items.titanium, 120, Items.thorium, 100, Items.surgeAlloy, 55));
            hasPower = true;
            hasLiquids = true;
            hasItems = true;
            itemCapacity = 12;
            consumePower(7);
            outputItem = new ItemStack(EUItems.lightninAlloy, 2);
            craftTime = 3 * 60f;
            size = 4;
            consumeItems(with(Items.surgeAlloy, 3, Items.phaseFabric, 2, Items.blastCompound, 3));
            consumeLiquid(Liquids.cryofluid, 0.1f);
            craftEffect = EUFx.diffuse(size, EUItems.lightninAlloy.color, 60);
            ambientSound = Sounds.techloop;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(new DrawDefault(), new DrawLiquidRegion(), new DrawFlame(Color.valueOf("ffef99")), new DrawLA(Pal.surge, 1.6f * 8));
        }};

        ELA = new HeatCrafter("LA-E"){{
            requirements(Category.crafting, with(Items.graphite, 300, Items.silicon, 250, Items.tungsten, 250, Items.oxide, 200, Items.surgeAlloy, 200));
            size = 5;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(), new DrawDefault(), new DrawHeatInput(), new DrawLAE(new Color[]{Color.valueOf("f58349"), Color.valueOf("f58349"), EUItems.lightninAlloy.color}, 1f * 8, 3.2f), new DrawCrucibleFlame());

            craftEffect = new MultiEffect(new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 11f), EUFx.diffuse(size, EUItems.lightninAlloy.color, 60));

            ambientSound = Sounds.fire;
            ambientSoundVolume = 0.3f;

            hasLiquids = true;
            itemCapacity = 24;
            liquidCapacity = 60f;
            consumePower(4f);
            heatRequirement = 12f;
            consumeItems(with(Items.surgeAlloy, 4, Items.phaseFabric, 3));
            consumeLiquid(Liquids.nitrogen, 0.1f);
            outputItem = new ItemStack(EUItems.lightninAlloy, 3);

            maxEfficiency = 3;
            craftTime = 4 * 60f;
        }};

        thermalHeater = new ThermalHeater("thermal-heater"){{
            requirements(Category.power, with(Items.graphite, 50, Items.beryllium, 100, Items.oxide, 15));
            powerProduction = 70/60f;
            generateEffect = Fx.redgeneratespark;
            effectChance = 0.01f;
            size = 2;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            canOverdrive = true;
            basicHeatOut = 2f;
        }};
        ventHeater = new ThermalHeater("vent-heater"){{
            requirements(Category.crafting, with(Items.graphite, 70, Items.tungsten, 80, Items.oxide, 50));
            attribute = Attribute.steam;
            group = BlockGroup.liquids;
            displayEfficiencyScale = 1f / 9f;
            minEfficiency = 9f - 0.0001f;
            displayEfficiency = false;
            generateEffect = Fx.turbinegenerate;
            effectChance = 0.04f;
            size = 3;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;

            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
            powerProduction = 0;
            hasPower = false;
            basicHeatOut = 15/9f;
            outputsLiquid = true;
            outputLiquid = new LiquidStack(Liquids.water, 3f/60f/9f);
            sec = 9;
        }

            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.basePowerGeneration);
            }
        };
        largeElectricHeater = new HeatProducer("large-electric-heater"){{
            requirements(Category.crafting, with(Items.beryllium, 100, Items.tungsten, 65, Items.oxide, 75, Items.silicon, 90, Items.carbide, 50));

            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
            rotateDraw = false;
            size = 5;
            heatOutput = 25f;
            regionRotated1 = 1;
            ambientSound = Sounds.hum;
            itemCapacity = 0;
            consumePower(600f / 60f);
        }};
        slagReheater = new HeatProducer("slag-reheater"){{
            requirements(Category.crafting, with(Items.tungsten, 30, Items.oxide, 30, Items.beryllium, 20));

            researchCostMultiplier = 3f;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.slag), new DrawDefault(), new DrawHeatOutput());
            size = 2;
            liquidCapacity = 24f;
            rotateDraw = false;
            regionRotated1 = 1;
            ambientSound = Sounds.hum;
            consumeLiquid(Liquids.slag, 24f / 60f);
            heatOutput = 5f;
        }

            @Override
            public void setStats() {
                super.setStats();
                stats.remove(Stat.productionTime);
            }
        };
        heatTransfer = new HeatConductor("heat-transfer"){{
            requirements(Category.crafting, with(Items.tungsten, 10, Items.graphite, 8, Items.oxide, 5));
            size = 2;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
            researchCostMultiplier = 5f;//因为已经解锁大的了，再多不好吧awa
        }};
        heatDistributor = new HeatConductor("heat-distributor"){{
            requirements(Category.crafting, with(Items.tungsten, 10, Items.graphite, 6, Items.oxide, 5));

            researchCostMultiplier = 5f;

            size = 2;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(-1, false), new DrawHeatOutput(), new DrawHeatOutput(1, false), new DrawHeatInput("-heat"));
            regionRotated1 = 1;
            splitHeat = true;
        }};
        heatDriver = new HeatDriver("heat-driver"){{
            requirements(Category.crafting, with(Items.tungsten, 150, Items.silicon, 120, Items.oxide, 130, Items.carbide, 60));
            size = 3;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"), new DrawHeatDriver());
            range = 360;
            regionRotated1 = 1;
            if(hardMod) lost = 0.2f;

            consumePower(4);
        }};


        liquidConsumeGenerator = new ConsumeGenerator("liquid-generator"){{
            requirements(Category.power, with(Items.graphite, 120, Items.silicon, 115, Items.thorium, 65, Items.phaseFabric, 20));
            size = 3;
            powerProduction = 660/60f;
            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawWarmupRegion(){{
                        sinMag = 0;
                        sinScl = 1;
                    }},
                    new DrawLiquidRegion()
            );
            consume(new ConsumeLiquidFlammable(0.4f, 0.2f));
            hasLiquids = true;
            generateEffect = new RadialEffect(new Effect(160f, e -> {
                color(Color.valueOf("6E685A"));
                alpha(0.6f);

                Rand rand = Fx.rand;
                Vec2 v = Fx.v;

                rand.setSeed(e.id);
                for(int i = 0; i < 3; i++){
                    float len = rand.random(6f), rot = rand.range(40f) + e.rotation;

                    e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                        v.trns(rot, len * b.finpow());
                        Fill.circle(e.x + v.x, e.y + v.y, 2f * b.fslope() + 0.2f);
                    });
                }
            }), 4, 90, 8f);
            effectChance = 0.2f;
        }};
        thermalReactor = new ThermalReactor("T2ther"){{
            requirements(Category.power, with(Items.silicon, 95, Items.titanium, 70, Items.thorium, 55, Items.metaglass, 65, Items.plastanium, 60, Items.surgeAlloy, 30));
            size = 3;
            if(!hardMod) powerProduction = 276/60f;
            else powerProduction = 220/60f;
            generateEffect = Fx.none;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
        }};

        heatPower = new SpaceGenerator("heatPower"){{
            requirements(Category.power, with(Items.thorium, 150, Items.silicon, 150, Items.graphite, 200, Items.surgeAlloy, 80));
            size = 3;
            haveBasicPowerOutput = false;
            attribute = Attribute.heat;
            blockedOnlySolid = true;
            powerProduction = 45/60f;
            outEffect = new RadialEffect(EUFx.absorbEffect2, 4, 90f, 7f);
            outTimer = EUFx.absorbEffect2.lifetime;
            drawer = new DrawMulti(new DrawDefault(), new DrawBlock() {
                @Override
                public void draw(Building build) {
                    Draw.color(Items.pyratite.color);
                    Draw.alpha(build.warmup());
                    Draw.rect(Core.atlas.find(name + "-heat"), build.x, build.y);
                }
            });
        }};
        windPower = new SpaceGenerator("windPower"){{
            requirements(Category.power, with(Items.graphite, 300, Items.silicon, 200, Items.titanium, 100, EUItems.crispSteel, 80, Items.plastanium, 55));
            space = 2;
            size = 3;
            if(hardMod) powerProduction = 7/60f;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rot", 4));
            tileEffect = new Effect(220, (e) -> {
                float length = 3f + e.finpow() * 20f;
                rand.setSeed(e.id);

                for(int i = 0; i < 6; ++i) {
                    v.trns(rand.random(360), rand.random(length));
                    float sizer = rand.random(1f, 2f);
                    e.scaled(e.lifetime * rand.random(0.5f, 1), (b) -> {
                        Draw.color(Color.gray, b.fslope() * 0.93f);
                        Fill.circle(e.x + v.x, e.y + v.y, sizer + b.fslope());
                    });
                }

            });

            canOverdrive = false;
        }};
        waterPower = new SpaceGenerator("waterPower"){{
            requirements(Category.power, with(Items.graphite, 300, Items.silicon, 250, Items.surgeAlloy, 150, Items.phaseFabric, 120, EUItems.crispSteel, 150));
            size = 3;
            attribute = Attribute.water;
            attributeColor = Color.blue;
            negativeAttributeColor = Color.white;
            if(hardMod) powerProduction = 6/60f;
            else powerProduction = 8.5f/60f;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rot", 4));

            tileEffect = new Effect(220, (e) -> {
                float length = 3f + e.finpow() * 20f;
                rand.setSeed(e.id);

                for(int i = 0; i < 5; ++i) {
                    v.trns(rand.random(360), rand.random(length));
                    float sizer = rand.random(1f, 2f);
                    e.scaled(e.lifetime * rand.random(0.5f, 1), (b) -> {
                        Draw.color(Color.valueOf("39c5bb"), b.fslope() * 0.93f);
                        Fill.circle(e.x + v.x, e.y + v.y, sizer + b.fslope());
                    });
                }

            });

            canOverdrive = false;
        }};
        LG = new LightenGenerator("lightnin-generator"){{
            requirements(Category.power, with(Items.metaglass, 600, Items.graphite, 550, Items.silicon, 470, Items.surgeAlloy, 550, EUItems.lightninAlloy, 270));
            fuelItem = EUItems.lightninAlloy;
            consumeItem(fuelItem);
            liquidCapacity = 60;
            size = 6;
            itemCapacity = 30;
            heating = 0.04f;
            health = 6000;
            itemDuration = 180;
            powerProduction = 17280/60f;
            explosionRadius = 30 * 8;
            explosionDamage = 12000;
            coolantPower = 0.1f;

            BulletType bi = new BulletType(){{
                damage = 0;
                speed = 6;
                lifetime = 60;
                hitEffect = Fx.none;
                despawnEffect = Fx.none;
                ammoMultiplier = 3;
                homingPower = 0.8f;
                homingRange = 10 * 8f;
                hittable = absorbable = collides = collidesGround = collidesAir = collidesTiles = false;
                keepVelocity = false;
                trailEffect = new Effect(12, e ->{
                    Draw.color(EUItems.lightninAlloy.color);
                    Drawf.tri(e.x, e.y, 4 * e.fout(), 11, e.rotation);
                    if(e.data instanceof Float){
                        float time = (float) e.data;
                        Drawf.tri(e.x, e.y, 4 * e.fout(), 15 * Math.min(1, time / 8 * 0.8f + 0.2f), e.rotation - 180);
                    }
                });
            }
                @Override
                public void update(Bullet b) {
                    b.rotation(b.rotation() + 4f * Time.delta);
                    if(b.time < b.lifetime && b.timer.get(1, 10))
                        EUFx.chainLightningFade.at(b.x, b.y, 8, EUItems.lightninAlloy.color, b.data);
                    trailEffect.at(b.x, b.y, b.rotation(), b.time);
                }

                @Override
                public void draw(Bullet b) {
                    Draw.color(EUItems.lightninAlloy.color);
                    Drawf.tri(b.x, b.y, 4, 8, b.rotation());
                    Draw.reset();
                }
            };
            BulletType bd = new BulletType(){{
                damage = explosionDamage;
                splashDamageRadius = explosionRadius;
                hittable = absorbable = collides = collidesGround = collidesAir = collidesTiles = false;
                hitEffect = despawnEffect = none;
                keepVelocity = false;
                speed = 0;
                lifetime = 150;
            }

                @Override
                public void update(Bullet b) {
                    Seq<Healthc> seq = new Seq<>();
                    float r = splashDamageRadius * (1 - b.foutpow());
                    Vars.indexer.allBuildings(b.x, b.y, r, seq::addUnique);
                    Units.nearby(b.x - r, b.y - r, r * 2, r * 2, u -> {
                        if(u.type != null && u.type.targetable && b.within(u, r)) seq.addUnique(u);
                    });
                    for(int i = 0; i < seq.size; i++){
                        Healthc hc = seq.get(i);
                        if(hc != null && !hc.dead()) {
                            if(!b.hasCollided(hc.id())) {
                                if(hc.health() <= damage) hc.kill();
                                else hc.health(hc.health() - damage);
                                b.collided.add(hc.id());
                            }
                        }
                    }
                }

                @Override
                public void draw(Bullet b) {
                    float r = splashDamageRadius * (1 - b.foutpow());
                    Lines.stroke(3 + 32 * (1 - b.finpow()), EUItems.lightninAlloy.color);
                    Lines.circle(b.x, b.y, splashDamageRadius * (1 - b.foutpow()));
                    for(float i = 0; i < r/2; i += 0.2f){
                        float a = i/r;
                        float rr = r * a + r/2;
                        Draw.alpha(a);
                        Lines.stroke(0.2f);
                        Lines.circle(b.x, b.y, rr);
                    }
                    if(b.time < b.lifetime - EUFx.chainLightningFade.lifetime && b.timer.get(1, 1)){
                        float ag = Mathf.random(360);
                        float px = EUGet.dx(b.x, r, ag), py = EUGet.dy(b.y, r, ag);
                        EUFx.chainLightningFade.at(b.x, b.y, 10, EUItems.lightninAlloy.color, EUGet.pos(px, py));
                        //EUFx.chainLightningFade.at(px, py, 10, EUItems.lightninAlloy.color, b);
                    }
                }
            };
            BulletType fbd = new fBullet(bd, 20){{
                hitSound = despawnSound = Sounds.explosionbig;
                hitSoundVolume = 2;
            }};
            Effect g1 = EUFx.gone(EUItems.lightninAlloy.color, size * 8 * 1.6f, 6);
            Effect g2 = new ExplosionEffect(){{
                lifetime = 24f;
                waveStroke = 5f;
                waveLife = 8f;
                waveColor = EUItems.lightninAlloy.color;
                sparkColor = EUItems.lightninAlloy.color;
                smokeColor = EUItems.lightninAlloy.color;
                waveRad = 8 * 8;
                smokeSize = 4;
                smokes = 7;
                sparks = 5;
                sparkRad = 6 * 8;
                sparkLen = 4f;
                sparkStroke = 1.7f;
            }};
            deathBullet = new BulletType(){{
                damage = 0;
                splashDamage = explosionDamage;
                splashDamageRadius = explosionRadius;
                lifetime = 360;
                speed = 0;
                intervalBullet = EUBulletTypes.ib;
                intervalDelay = 6;
                despawnEffect = new Effect(fbd.lifetime, e -> {
                    Lines.stroke(3, EUItems.lightninAlloy.color);
                    Lines.circle(e.x, e.y, 10 * tilesize * e.fout());
                });
                hittable = absorbable = collides = collidesGround = collidesAir = collidesTiles = false;
                ammoMultiplier = 1;
            }

                @Override
                public void update(Bullet b) {
                    if(b.time < b.lifetime && b.timer.get(1, 18 * b.fout() + 6)){
                        bi.create(b, b.team, b.x, b.y, Mathf.random(360), -1, 1, 1, EUGet.pos(b.x, b.y));
                        g1.at(b);
                        Sounds.malignShoot.at(b);
                    }
                    if(b.timer.get(2, intervalDelay)){
                        float bx = b.x + Mathf.random(-size * tilesize, size * tilesize), by = b.y + Mathf.random(-size * tilesize, size * tilesize);
                        g2.at(bx, by);
                        EUFx.chainLightningFade.at(b.x, b.y, 6, EUItems.lightninAlloy.color, EUGet.pos(bx, by));
                        intervalBullet.create(b, b.team, bx, by, Mathf.random(360), -1, 1, 1, 0f);
                    }
                }

                @Override
                public void draw(Bullet b) {
                    Lines.stroke(3f, EUItems.lightninAlloy.color);
                    float pow = Math.min(b.finpow() * 2, 1);
                    Lines.circle(b.x, b.y, 10 * tilesize * pow);
                    Fill.circle(b.x, b.y, 2 * tilesize);
                    for(int i = 0; i < 2; i++){
                        float a1 = i * 180 + b.time * 2;
                        float a2 = i * 180 - b.time * 2;
                        Drawf.tri(b.x, b.y, 10, 15 * tilesize * pow, a1);
                        Drawf.tri(b.x, b.y, 7, 6 * tilesize, a2);
                    }
                }

                @Override
                public void despawned(Bullet b) {
                    despawnEffect.at(b);
                    if(state.rules.reactorExplosions) fbd.create(b, b.team, b.x, b.y, 0);
                }
            };

            consumeLiquid(Liquids.cryofluid, heating / coolantPower).update(false);
        }};


        dissipation = new dissipation("dissipation"){{
            requirements(Category.turret, with(Items.silicon, 180, Items.thorium, 100,EUItems.lightninAlloy, 60, Items.phaseFabric, 80));
            hasPower = true;
            size = 3;
            range = 220;
            shootCone = 36;
            rotateSpeed = 12;
            shootLength = 8;
            health = 250 * 3 * 3;
            coolantMultiplier = 5;
            coolant = consumeCoolant(0.3f);
            if(!hardMod) consumePower(12f);
            else consumePower(18f);
        }};

        guiY = new guiY("guiY"){{
            requirements(Category.turret, with(Items.beryllium, 65, Items.graphite, 90, Items.silicon, 66));
            size = 2;
            ammo(
                    Items.silicon, new CtrlMissile(name("carrot"), 20, 20){{
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootBigSmoke2;
                        speed = 4.3f;
                        keepVelocity = false;
                        maxRange = 6f;
                        lifetime = 60f;
                        damage = 100 - (hardMod ? 15 : 0);
                        splashDamage = 120 - (hardMod ? 20 : 0);
                        splashDamageRadius = 32;
                        buildingDamageMultiplier = 0.8f;
                        absorbable = true;
                        hitEffect = despawnEffect = Fx.massiveExplosion;
                        trailColor = Pal.bulletYellowBack;
                        trailWidth = 1.7f;
                    }}
            );
            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart(){{
                            progress = PartProgress.warmup;
                            moveRot = -22f;
                            moveX = 0f;
                            moveY = -0.8f;
                            mirror = true;
                        }},
                        new RegionPart("-mid"){{
                            progress = PartProgress.recoil;
                            mirror = false;
                            under = true;
                            moveY = -0.8f;
                        }}
                );
            }};
            scaledHealth = 190;

            accurateDelay = false;

            range = 26.25f * 8;
            ammoPerShot = 2;
            maxAmmo = ammoPerShot * 4;
            shoot = new ShootAlternate(6f);
            shake = 2f;
            recoil = 1f;
            reload = 60f;
            shootY = 0f;
            rotateSpeed = 1.2f;
            minWarmup = 0.85f;
            shootWarmupSpeed = 0.07f;
            shootSound = Sounds.missile;

            coolant = consume(new ConsumeLiquid(Liquids.water, 12f / 60f));
        }};

        javelin = new PowerTurret("javelin"){{
            //1 + 1 = ⑨
            requirements(Category.turret, with(Items.surgeAlloy, 450, Items.silicon, 650, Items.carbide, 500, Items.phaseFabric, 300));
            consumePower(12f);
            heatRequirement = 45f;
            maxHeatEfficiency = 2f;
            range = 55 * 8;
            reload = 2 * 60;
            size = 5;
            shootSound = Sounds.malignShoot;
            shootWarmupSpeed = 0.06f;
            minWarmup = 0.9f;

            smokeEffect = Fx.none;
            rotateSpeed = 2.5f;
            recoil = 2f;
            recoilTime = 60f;

            Color bcr = Color.valueOf("c0ecff");
            Color bcrb = Color.valueOf("6d90bc");

            shootEffect = EUFx.ellipse(30, 30, 15, bcr);

            drawer = new DrawTurret("reinforced-"){{
                parts.add(
                        new JavelinWing(){{
                            x = 0;
                            y = -7;
                            layer = Layer.effect;
                        }},
                        new BowHalo(){{
                            progress = PartProgress.warmup.delay(0.8f);
                            x = 0;
                            y = 18;
                            stroke = 3;
                            w2 = h2 = 0;
                            w1 = 3;
                            h1 = 6;
                            radius = 4;
                            color = bcr;
                        }}
                );
            }};

            BulletType iceBar = new aimToPosBullet(){{
                damage = 180;
                splashDamage = 180;
                splashDamageRadius = 3 * 8;
                speed = 10;
                lifetime = 140;
                hitEffect = despawnEffect = new MultiEffect(new ExplosionEffect(){{
                    lifetime = 40f;
                    sparkColor = bcr;
                    waveRad = smokeSize = smokeSizeBase = 0f;
                    smokes = 0;
                    sparks = 5;
                    sparkRad = 4 * 8;
                    sparkLen = 5f;
                    sparkStroke = 2f;
                }}, new Effect(60, e -> {
                    DrawFunc.drawSnow(e.x, e.y, 2 * 8 * e.foutpow(), 0, bcr);
                }));
                trailInterval = 0.5f;
                trailEffect = new Effect(120, e -> {
                    Draw.color(bcr);
                   Fill.circle(e.x, e.y, 3 * e.foutpow());
                });
                trailLength = 16;
                trailWidth = 3;
                trailColor = bcr;

                buildingDamageMultiplier = 0.5f;
            }

                @Override
                public void draw(Bullet b) {
                    super.draw(b);
                    Draw.color(bcr);
                    Drawf.tri(b.x, b.y, 5, 12, b.rotation());
                    Drawf.tri(b.x, b.y, 5, 5, b.rotation() - 180);
                    Lines.stroke(1, bcrb);
                    Lines.lineAngle(b.x, b.y, b.rotation(), 9f);
                    Lines.lineAngle(b.x, b.y, b.rotation() - 180, 3f);
                }

                @Override
                public void update(Bullet b) {
                    super.update(b);
                    if(b.timer.get(1, 6)) EUFx.normalIceTrail.at(b.x + Mathf.random(-6, 6), b.y + Mathf.random(-6, 6), 7, bcr);
                }
            };
            int amount = 4;
            float spread = 40f;
            float inSpread = 5;

            shootType = new BulletType(){{
                reflectable = false;
                speed = 20;
                lifetime = 22;
                damage = 520;
                splashDamage = 390;
                splashDamageRadius = 8 * 8;
                trailColor = bcr;
                trailLength = 8;
                trailWidth = 5;
                trailEffect = new Effect(40, e -> {
                   DrawFunc.drawSnow(e.x, e.y, 12 * e.fout(), 360 * e.fin(), bcr);
                });
                trailInterval = 3;

                fragBullets = amount;
                fragBullet = iceBar;

                status = StatusEffects.freezing;

                hitEffect = despawnEffect = new ExplosionEffect(){{
                    lifetime = 40f;
                    waveStroke = 5f;
                    waveLife = 8f;
                    waveColor = bcrb;
                    sparkColor = bcr;
                    waveRad = 8 * 8;
                    smokeSize = smokes = 0;
                    smokeSizeBase = 0f;
                    sparks = 6;
                    sparkRad = 10 * 8;
                    sparkLen = 7f;
                    sparkStroke = 3f;
                }};
                shootEffect = smokeEffect = Fx.none;
                buildingDamageMultiplier = 0.5f;
            }

                @Override
                public void hitEntity(Bullet b, Hitboxc entity, float health) {
                    if(!pierce || b.collided.size >= pierceCap) explode(b);
                    super.hitEntity(b, entity, health);
                }

                @Override
                public void hit(Bullet b) {
                    explode(b);
                    super.hit(b);
                }

                public void explode(Bullet b){
                    if(!(b.owner instanceof PowerTurretBuild)) return;
                    PowerTurretBuild tb = (PowerTurretBuild) b.owner;
                    for(int i = 0; i < amount; i++){
                        float angleOffset = i * spread - (amount - 1) * spread / 2f;
                        iceBar.create(tb, tb.team, tb.x, tb.y, tb.rotation - 180 + angleOffset + Mathf.random(-inSpread, inSpread), -1, 1, 1, new Position[]{EUGet.pos(tb.x, tb.y), EUGet.pos(b.x, b.y)});
                    }
                }

                @Override
                public void createFrags(Bullet b, float x, float y) { }

                @Override
                public void draw(Bullet b) {
                    super.draw(b);
                    Draw.color(bcr);
                    Drawf.tri(b.x, b.y, 15, 18, b.rotation());
                    Drawf.tri(b.x, b.y, 15, 6, b.rotation() - 180);
                    Lines.stroke(1, bcrb);
                    Lines.lineAngle(b.x, b.y, b.rotation(), 15f);
                    Lines.lineAngle(b.x, b.y, b.rotation() - 180, 4f);
                }
            };
        }};

        // 梦幻联动
        onyxBlaster = new MultiBulletTurret("onyx-blaster"){{
            requirements(Category.turret, with(Items.graphite, 200, Items.silicon, 220, Items.thorium, 250, Items.surgeAlloy, 150));
            size = 4;
            health = 3200;
            int blockId = id;
            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart(){{
                              progress = PartProgress.warmup;
                              moveRot = -18f;
                              moveX = 2f;
                              moveY = -0.8f;
                              mirror = true;
                          }},
                        new RegionPart("-mid"){{
                            progress = PartProgress.recoil;
                            mirror = false;
                            under = true;
                            moveY = -0.8f;
                        }},
                        new DrawBall(){{
                            y = 5;
                            bColor = Pal.sapBullet;
                            id = blockId;
                            layer = Layer.effect;
                        }}
                );
            }};
            minWarmup = 0.9f;

            rotateSpeed = 4.5f;
            all = true;
            range = 36 * 8;
            reload = 60;
            recoil = 4;
            coolant = consumeCoolant(0.5f);
            coolantMultiplier = 3;
            shootSound = Sounds.shootAltLong;
            smokeEffect = new Effect(20, e -> {
                Draw.color(Pal.sap);
                Angles.randLenVectors(e.id, 5, 20 * e.fin(), e.rotation, 30, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 5 * e.fout());
                });
            });
            shootEffect = new Effect(20, e -> {
                Draw.color(Pal.sapBullet);
                Angles.randLenVectors(e.id, 5, 20 * e.fin(), e.rotation, 30, (x, y) -> {
                    Lines.stroke(2 * e.fout());
                    float ag = Mathf.angle(x, y);
                    Lines.lineAngle(e.x + x, e.y + y, ag, 5);
                });
            });
            BulletType bb1 = new BulletType(){{
                homingPower = 1;
                homingRange = 10 * 8;
                damage = 88;
                speed = 9;
                lifetime = 30;
                trailWidth = 1;
                trailColor = Pal.heal;
                trailLength = 12;
                absorbable = false;
                hitEffect = despawnEffect = Fx.none;
            }};
            BulletType b1 = new BulletType(){{
                ammoMultiplier = 2;
                fragBullet = bb1;
                fragBullets = 4;
                fragVelocityMin = 1;
                fragRandomSpread = 30;
                damage = 0;
                speed = 0;
                lifetime = 0;
                hittable = false;
                absorbable = false;
                despawnEffect = hitEffect = Fx.none;
            }};

            BulletType bb2 = new BasicBulletType(){{
                damage = 55;
                speed = 9;
                sprite = name("shotgunShot");
                width = 3;
                height = 7;
                frontColor = Pal.sapBullet;
                lifetime = 30;
                hitEffect = despawnEffect = Fx.none;
                fragBullet = new BasicBulletType(){{
                    damage = 26;
                    frontColor = Pal.sapBullet;
                    width = 3;
                    height = 2;
                    shrinkY = 0;
                    speed = 10;
                    lifetime = 5;
                    hitEffect = despawnEffect = Fx.none;
                }};
                fragBullets = 6;
            }};
            BulletType b2 = new BulletType(){{
                ammoMultiplier = 2;
                fragBullet = bb2;
                fragBullets = 4;
                fragVelocityMin = 1;
                fragRandomSpread = 30;
                damage = 0;
                speed = 0;
                lifetime = 0;
                hittable = false;
                absorbable = false;
                despawnEffect = hitEffect = Fx.none;
            }};

            BulletType bb3 = new BasicBulletType(){{
                damage = 66;
                speed = 9;
                sprite = name("shotgunShot");
                width = 3;
                height = 7;
                frontColor = Items.thorium.color;
                lifetime = 30;
                hitEffect = despawnEffect = Fx.none;
            }};
            BulletType b3 = new BulletType(){{
                ammoMultiplier = 1;
                fragBullet = bb3;
                fragBullets = 4;
                fragVelocityMin = 1;
                fragRandomSpread = 30;
                damage = 0;
                speed = 0;
                lifetime = 0;
                hittable = false;
                absorbable = false;
                despawnEffect = hitEffect = Fx.none;
            }};

            BulletType bb4 = new BulletType(){{
                damage = 90;
                speed = 9;
                trailLength = 10;
                trailWidth = 1;
                trailColor = Color.valueOf("00EAFF");
                lifetime = 30;
                pierce = true;
                pierceBuilding = true;
                absorbable = false;
                hitEffect = despawnEffect = Fx.none;
            }};
            BulletType b4 = new BulletType(){{
                ammoMultiplier = 2;
                fragBullet = bb4;
                fragBullets = 4;
                fragVelocityMin = 1;
                fragRandomSpread = 30;
                damage = 0;
                speed = 0;
                lifetime = 0;
                hittable = false;
                absorbable = false;
                despawnEffect = hitEffect = Fx.none;
            }};

            BulletType bs = new BasicBulletType(9, 120, name("onyx-blaster-bullet")){{
                splashDamage = 120;
                splashDamageRadius = 10 * 8;
                lifetime = 30;
                width = height = 18;
                shrinkY = 0;
                status = StatusEffects.sapped;

                hitEffect = despawnEffect = new ExplosionEffect(){{
                    lifetime = 40f;
                    waveStroke = 5f;
                    waveLife = 8f;
                    waveColor = Pal.sap;
                    sparkColor = Pal.sapBulletBack;
                    smokeColor = Pal.sapBullet;
                    waveRad = 10 * 8;
                    smokeSize = 4;
                    smokes = 7;
                    smokeSizeBase = 0f;
                    sparks = 5;
                    sparkRad = 10 * 8;
                    sparkLen = 5f;
                    sparkStroke = 2f;
                }};

                ammoMultiplier = 1;
            }};
            BulletType[] bullets1 = new BulletType[]{b3, bs};
            BulletType[] bullets2 = new BulletType[]{b2, bs};
            BulletType[] bullets3 = new BulletType[]{b4, bs};
            BulletType[] bullets4 = new BulletType[]{b1, bs};
            ammo(Items.thorium, bullets1, Items.carbide, bullets2, Items.surgeAlloy, bullets3, EUItems.lightninAlloy, bullets4);
        }};
        celebration = new MultiBulletTurret("celebration"){{
            requirements(Category.turret, with(Items.silicon, 120, Items.titanium, 125, Items.thorium, 70, EUItems.crispSteel, 60));
            drawer = new DrawTurret("reinforced-");
            shoot = new ShootSpread(2, 4);
            inaccuracy = 3;
            scaledHealth = 180;
            size = 3;
            range = 27f * 8;
            shake = 2f;
            recoil = 1f;
            reload = 60f;
            shootY = 12f;
            rotateSpeed = 3.2f;
            coolant = consumeCoolant(0.3f);
            shootSound = Sounds.missile;

            BulletType f1 = new FireWorkBullet(100, 4, name("mb"), Color.valueOf("EA8878"), 6 * 8);
            BulletType f2 = new FireWorkBullet(100, 4, Color.valueOf("5CFAD5"));
            BulletType f3 = new FireWorkBullet(100, 4){{
                colorful = true;
                fire = new colorFire(false, 3f, 60){{
                    stopFrom = 0f;
                    stopTo = 0f;
                    trailLength = 9;
                }};
                splashDamageRadius = 10 * Vars.tilesize;
                trailInterval = 0;
                trailWidth = 2;
                trailLength = 8;
            }};
            BulletType fp1 = new FireWorkBullet(88, 4, name("mb"), Color.valueOf("EA8878"), 6 * 8){{
                status = StatusEffects.none;
            }};
            BulletType fp2 = new FireWorkBullet(88, 4, Color.valueOf("5CFAD5")){{
                status = StatusEffects.none;
            }};
            BulletType fp3 = new FireWorkBullet(88, 4, Items.plastanium.color){{
                fire = new colorFire(true, 5f, 60){{
                    trailLength = 9;
                    stopFrom = 0.1f;
                    stopTo = 0.7f;
                }};
                splashDamageRadius = 8 * Vars.tilesize;
            }};
            BulletType[] bullets1 = new BulletType[]{f1, f2, f3};
            BulletType[] bullets2 = new BulletType[]{fp1, fp2, fp3};
            ammo(Items.blastCompound, bullets1, Items.plastanium, bullets2, EUItems.lightninAlloy);
        }};

        celebrationMk2 = new MultiBulletTurret("celebration-mk2"){{
            size = 5;
            drawer = new DrawMulti(new DrawTurret("reinforced-"), new DrawMk2());
            requirements(Category.turret, with(Items.silicon, 410, Items.graphite, 520, Items.thorium, 380, EUItems.lightninAlloy, 280));
            inaccuracy = 3;
            shootEffect = EUFx.Mk2Shoot(90);
            smokeEffect = Fx.none;
            scaledHealth = 180;
            range = 32 * 8;
            shake = 2f;
            recoil = 2f;
            reload = 10;
            shootY = 20;
            rotateSpeed = 2.6f;
            coolant = consumeCoolant(0.8f);
            coolantMultiplier = 1.5f;
            shootSound = Sounds.missile;
            shootCone = 16;
            canOverdrive = false;
            maxAmmo = 10;

            //红
            BulletType f1 = new FireWorkBullet(120, 5, name("mb-mk2"), Color.valueOf("FF1A44"), 6 * 8){{
                outline = true;
                trailInterval = 20;
                trailEffect = new ExplosionEffect(){{
                    lifetime = 60f;
                    waveStroke = 5f;
                    waveLife = 8f;
                    waveColor = Color.white;
                    sparkColor = Pal.lightOrange;
                    smokeColor = Pal.darkerGray;
                    waveRad = 0;
                    smokeSize = 4;
                    smokes = 7;
                    smokeSizeBase = 0f;
                    sparks = 10;
                    sparkRad = 3 * 8;
                    sparkLen = 6f;
                    sparkStroke = 2f;
                }};
                trailWidth = 2.4f;
                trailLength = 10;
                pierce = true;
                pierceCap = 3;
                fire = new colorFire(false, 2.3f, 60){{
                    stopFrom = 0.55f;
                    stopTo = 0.55f;
                    rotSpeed = 666;
                }};
                num = 15;
            }};
            //橙
            BulletType ff2 = new FireWorkBullet(150, 6.7f, name("mb-mk2"), Color.valueOf("FFB22C"), 12 * 8){{
                outline = true;
                trailWidth = 3.5f;
                trailLength = 10;
                trailInterval = 0;
                width = 22;
                height = 22;
                fire = new colorFire(false, 3.6f, 60){{
                    stopFrom = 0.7f;
                    stopTo = 0.7f;
                    rotSpeed = 666;
                    hittable = true;
                    //speedRod = 0.3f;
                }};
                textFire = new spriteBullet(name("fire-EU"));
                status = StatusEffects.none;
                num = 18;
            }

                @Override
                public void update(Bullet b) {
                    super.update(b);
                    b.rotation(b.rotation() + Time.delta * 3f);
                    if(b.timer.get(3, 6)) EUFx.ellipse(14, 8/2, 40, color).at(b.x, b.y, b.rotation());
                }
            };
            BulletType f2 = new BulletType(){{
                ammoMultiplier = 1;
                damage = 0;
                speed = 0;
                lifetime = 0;
                fragBullet = ff2;
                fragBullets = 1;
                collides = false;
                absorbable = false;
                hittable = false;
                despawnEffect = hitEffect = Fx.none;
            }
                public void createFrags(Bullet b, float x, float y){
                    if(fragBullet != null && (fragOnAbsorb || !b.absorbed)){
                        fragBullet.create(b, b.x, b.y, b.rotation() - 60);
                    }
                }
            };
            //黄
            BulletType f3 = new FireWorkBullet(120, 5, name("mb-mk2"), Color.valueOf("FFF52B"), 6 * 8){{
                outline = true;
                trailInterval = 0;
                trailWidth = 2f;
                trailLength = 10;
                weaveMag = 8f;
                weaveScale = 2f;
                fire = new colorFire(false, 2.3f, 60){{
                    stopFrom = 0.55f;
                    stopTo = 0.55f;
                    rotSpeed = 666;
                    hittable = true;
                }};
                textFire = new spriteBullet(name("fire-Carrot"));
                status = StatusEffects.none;
                num = 18;
            }};
            //绿
            BulletType f4 = new FireWorkBullet(120, 5, name("mb-mk2"), Color.valueOf("2BFF5C"), 6 * 8){{
                outline = true;
                trailInterval = 0;
                trailWidth = 2.4f;
                trailLength = 10;
                homingPower = 1;
                homingRange = 32 * 8;
                width = 10;
                height = 10;
                status = StatusEffects.electrified;
                fire = new colorFire(false, 2.3f, 60){{
                    stopFrom = 0.55f;
                    stopTo = 0.55f;
                    rotSpeed = 666;
                }};
                num = 10;
            }};
            //蓝
            BulletType ff5 = new FireWorkBullet(110, 6, name("mb-mk2"), Color.valueOf("006AFF"), 8 * 8){{
                outline = true;
                trailInterval = 0;
                trailWidth = 3f;
                trailLength = 10;
                width = 19;
                height = 19;
                status = StatusEffects.wet;
                weaveMag = 8;
                weaveScale = 6;
                weaveRandom = false;
                fire = new colorFire(false, 2.8f, 60){{
                    stopFrom = 0.55f;
                    stopTo = 0.55f;
                    rotSpeed = 666;
                    //speedRod = 0.3f;
                }};
                num = 20;
            }
                public void updateWeaving(Bullet b){
                    if(weaveMag != 0 && b.data instanceof Integer){
                        b.vel.rotateRadExact((float)Math.sin((b.time + Math.PI * weaveScale/2f) / weaveScale) * weaveMag * Time.delta * Mathf.degRad * (int)b.data);
                    }
                }
            };
            BulletType f5 = new BulletType(){{
                ammoMultiplier = 1;
                damage = 0;
                speed = 0;
                lifetime = 0;
                fragBullet = ff5;
                fragBullets = 2;
                collides = false;
                absorbable = false;
                hittable = false;
                despawnEffect = hitEffect = Fx.none;
            }
                public void createFrags(Bullet b, float x, float y){
                    if(fragBullet != null && (fragOnAbsorb || !b.absorbed)){
                        for(int i : new int[]{-1, 1}) fragBullet.create(b, b.team, b.x, b.y, b.rotation() - 10 * i, -1, 1, 1, i);
                    }
                }
            };
            //紫
            BulletType ff6 = new FireWorkBullet(100, 5, name("mb-mk2"), Color.valueOf("B72BFF"), 4 * 8){{
                outline = true;
                trailInterval = 0;
                trailWidth = 2f;
                trailLength = 10;
                width = 9;
                height = 9;
                status = StatusEffects.sapped;
                fire = new colorFire(true, 4, 60){{
                    hittable = true;
                }};
            }

                @Override
                public void update(Bullet b) {
                    super.update(b);
                    if(b.data instanceof Float){
                        if(b.time > 10) b.rotation(Angles.moveToward(b.rotation(), (float) b.data, Time.delta * 0.5f));
                    }
                }
            };
            BulletType f6 = new BulletType(){{
                ammoMultiplier = 1;
                damage = 0;
                speed = 0;
                lifetime = 0;
                fragBullet = ff6;
                fragBullets = 3;
                collides = false;
                absorbable = false;
                hittable = false;
                despawnEffect = hitEffect = Fx.none;
            }
                public void createFrags(Bullet b, float x, float y){
                    if(fragBullet != null && (fragOnAbsorb || !b.absorbed)){
                        for(int i : new int[]{-1, 0, 1}) fragBullet.create(b, b.team, b.x, b.y, b.rotation() - 10 * i, -1, 1, 1, b.rotation());
                    }
                }
            };
            //粉
            BulletType f7 = new FireWorkBullet(125, 5, name("mb-mk2"), Color.valueOf("FF7DF4"), 10 * 8){{
                outline = true;
                trailInterval = 0;
                trailWidth = 2.4f;
                trailLength = 10;
                status = StatusEffects.none;
                textFire = new spriteBullet(name("fire-guiY"), 128, 128);
                fire = new colorFire(false, 3f, 60){{
                    stopFrom = 0.6f;
                    stopTo = 0.6f;
                    rotSpeed = 666;
                    hittable = true;
                }};
            }};

            BulletType[] bullets = new BulletType[]{f1, f2, f3, f4, f5, f6, f7};
            ammo(Items.thorium, bullets);
        }};

        sancta = new ItemTurret("sancta"){{
            requirements(Category.turret, with(EUItems.lightninAlloy, 1500, Items.phaseFabric, 1800));
            size = 7;
            ammo(
                    EUItems.lightninAlloy,
                            new ScarletDevil(EUItems.lightninAlloy.color){{
                                speed = 16;
                                lifetime = 35;
                                trailColor = EUItems.lightninAlloy.color;
                                trailLength = 10;
                                trailWidth = 10;
                                splashDamage = damage = 1200 - (hardMod ? 300 : 0);
                                ammoMultiplier = 1;
                                hitSound = despawnSound = Sounds.explosionbig;
                                healColor = EUItems.lightninAlloy.color;
                                buildingDamageMultiplier = 0.7f;

                                fb.splashDamage = 80;
                                fb.splashDamageRadius = 3 * 8f;

                                if(hardMod) {
                                    fb.damage -= 15;
                                    ff.damage -= 15;
                                }
                            }

                                @Override
                                public void draw(Bullet b) {
                                    super.draw(b);
                                    Draw.color(Pal.surge);
                                    Drawf.tri(b.x, b.y, 20, 20, b.rotation());
                                    Drawf.tri(b.x, b.y, 10, 8, b.rotation()-180);
                                    Draw.z(Layer.flyingUnit);
                                    Draw.rect(Core.atlas.find(name("sancta-bt")), b.x, b.y, 32, 50, b.rotation() - 90);
                                }

                                @Override
                                public void update(Bullet b) {
                                    super.update(b);
                                    float x = b.x + Angles.trnsx(b.rotation() - 90, 0, -trailLength * 2);
                                    float y = b.y + Angles.trnsy(b.rotation() - 90, 0, -trailLength * 2);
                                    float rx = b.x + Angles.trnsx(b.rotation() - 90, Mathf.random(3), 0);
                                    float ry = b.y + Angles.trnsy(b.rotation() - 90, Mathf.random(3), 0);
                                    if(b.timer.get(2, 6)){
                                        Fx.chainLightning.at(rx, ry, b.rotation(), Pal.surge, EUGet.pos(x, y));
                                    }
                                }
                            }
            );
            drawer = new DrawMulti(
                    new DrawTurret(){{
                        parts.add(
                                new RegionPart("-behind"){{
                                    progress = PartProgress.warmup;
                                    moveY = 7f;
                                    mirror = false;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, -3f, 0f));
                                    under = true;
                                }},
                                new RegionPart("-mid"){{
                                    mirror = false;
                                }},
                                new RegionPart("-front"){{
                                    progress = PartProgress.warmup;
                                    moveY = 7f;
                                    mirror = false;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, -3f, 0f));
                                }}
                        );
                        parts.add(
                                new BowHalo(){{
                                    progress = PartProgress.warmup.delay(0.8f);
                                }}
                        );
            }}, new RunningLight(6), new DrawBow(){{
                arrowSp = name("sancta-bt");
            }}, new DrawTrail(2.5f, EUItems.lightninAlloy.color, 8));
            scaledHealth = 180;

            range = 80 * 8;
            ammoPerShot = 10;
            maxAmmo = ammoPerShot * 3;
            shake = 6f;
            recoil = 4f;
            reload = 360f;
            shootY = 0f;
            rotateSpeed = 1.2f;
            minWarmup = 0.95f;
            shootWarmupSpeed = 0.04f;
            shootSound = Sounds.largeCannon;

            coolant = consumeCoolant(2);
            coolantMultiplier = 0.5f;
            coolEffect = Fx.none;
            canOverdrive = false;
        }};

        fiammetta = new Fiammetta("fiammetta"){{
            requirements(Category.turret, with(EUItems.lightninAlloy, 280, Items.oxide, 400, Items.carbide, 240, Items.silicon, 500, Items.surgeAlloy, 300));
            size = 5;
            shake = 10;
            reload = 3 * 60;
            ammoPerShot = 8;
            maxAmmo = ammoPerShot * 3;
            range = 57.5f * 8;
            minRange = 20 * 8f;
            shootSound = Sounds.laserbig;
            recoil = 5;

            BulletType fall = new BulletType(){{
                speed = 0;
                lifetime = 20;
                collides = collidesTiles = hittable = absorbable = false;
                collidesAir = collidesGround = true;
                splashDamage = 1800;
                splashDamageRadius = 14 * 8f;
                despawnEffect = hitEffect = new MultiEffect(EUFx.expFtEffect(10, 15, splashDamageRadius, 30, 0.2f), EUFx.fiammettaExp(splashDamageRadius), new Effect(20, e -> {
                    Lines.stroke(16 * e.fout(), EUItems.lightninAlloy.color);
                    Lines.circle(e.x, e.y, (splashDamageRadius + 56) * e.fin());
                }));
                keepVelocity = false;
                buildingDamageMultiplier = 0.6f;

                hitSound = despawnSound = Sounds.explosionbig;
                despawnShake = hitShake = 8;
            }

                @Override
                public void draw(Bullet b) {
                    TextureRegion region = Core.atlas.find(name("mb-mk2"));
                    if(b.time < 10){
                        float fin = b.time/10, fout = 1 - fin;
                        float ww = 15 * 8, hh = 15 * 8 * fout;
                        Draw.color(EUItems.lightninAlloy.color);
                        Draw.alpha(fin);
                        Draw.rect(region, b.x, b.y, ww, hh, b.rotation() - 90);
                    }
                    Draw.color(EUItems.lightninAlloy.color);
                    Draw.alpha(b.fin());
                    Fill.circle(b.x, b.y, 20 * (b.time < 10 ? b.fin() * 2 : b.fout() * 2));
                }
            };

            Effect se = EUFx.aimEffect(40, EUItems.lightninAlloy.color, 1.5f, range, 13);
            ammo(
                    Items.surgeAlloy,
                    new BulletType(){{
                        chargeEffect = se;

                        ammoMultiplier = 1;
                        damage = 280;
                        splashDamageRadius = 10 * 8;
                        splashDamage = 400;
                        buildingDamageMultiplier = 1.2f;
                        lifetime = 30;
                        speed = 15;
                        pierce = true;
                        pierceBuilding = true;
                        hittable = false;
                        absorbable = false;
                        reflectable = false;
                        intervalBullet = new BulletType(){{
                            lifetime = 32;
                            speed = 0;
                            despawnEffect = hitEffect = new MultiEffect(new Effect(30, e -> {
                                float r = Math.min(10 * 8 * e.fin(), 6 * 8);
                                Draw.color(EUItems.lightninAlloy.color.cpy().a(e.fout()));
                                Fill.circle(e.x, e.y, r);
                                float ww = r * 2f, hh = r * 2f;
                                Draw.color(EUItems.lightninAlloy.color.cpy().a(e.fout()));
                                Draw.rect(Core.atlas.find(name("firebird-light")), e.x, e.y, ww, hh);
                            }), EUFx.expFtEffect(5, 12, 6 * 4, 30, 0.2f));
                            despawnSound = hitSound = Sounds.explosion;
                            collides = absorbable = hittable = false;
                            splashDamageRadius = 6 * 8;
                            splashDamage = 400;
                            buildingDamageMultiplier = 1.2f;
                        }

                            @Override
                            public void draw(Bullet b) {
                                super.draw(b);
                                float ft = (b.time > 16 ? b.fout() * 2 : 1);
                                Lines.stroke(5, EUItems.lightninAlloy.color);
                                Lines.circle(b.x, b.y, 12 * ft);
                                Lines.poly(b.x, b.y, 3, 12 * ft, b.fout() * 180);
                            }
                        };
                        intervalDelay = 4;
                        intervalSpread = intervalRandomSpread = 0;
                        bulletInterval = 4;
                        hitSize = 20;
                        despawnEffect = new MultiEffect(new Effect(30, e -> {
                            float r = Math.min(16 * 8 * e.fin(), 10 * 8);
                            Draw.color(EUItems.lightninAlloy.color.cpy().a(e.fout()));
                            Fill.circle(e.x, e.y, r);
                            float ww = r * 2f, hh = r * 2f;
                            Draw.color(EUItems.lightninAlloy.color.cpy().a(e.fout()));
                            Draw.rect(Core.atlas.find(name("firebird-light")), e.x, e.y, ww, hh);
                        }), EUFx.expFtEffect(6, 15, 10 * 4, 30, 0.2f));
                        despawnSound = Sounds.explosion;
                        hitEffect = Fx.none;
                        trailLength = 15;
                        trailColor = EUItems.lightninAlloy.color;
                        trailWidth = 4;
                        trailRotation = true;
                        trailEffect = new Effect(15, e ->{
                            color(e.color);
                            for(int x : new int[]{-20, 20}){
                                Tmp.v1.set(x, -10).rotate(e.rotation - 90);
                                Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 4 * e.foutpow());
                            }
                        });
                        trailInterval = 0.1f;
                    }

                        @Override
                        public void draw(Bullet b) {
                            super.draw(b);
                            Draw.color(EUItems.lightninAlloy.color);
                            Draw.rect(Core.atlas.find(name("phx")), b.x, b.y,48, 48,  b.rotation() - 90);
                            //Drawf.tri(b.x + Angles.trnsx(b.rotation(), 10), b.x + Angles.trnsy(b.rotation(), 10), 10, 20, b.rotation());
                        }
                    },
                    EUItems.lightninAlloy, new ArtilleryBulletType(){{
                        speed = 10;
                        ammoMultiplier = 8;
                        splashDamage = 1800;
                        splashDamageRadius = 14 * 8f;
                        hittable = absorbable = false;
                        collides = collidesTiles = false;
                        collidesAir = collidesGround = false;
                        despawnEffect = Fx.none;
                        hitEffect = Fx.none;
                        trailEffect = Fx.none;
                        fragOnHit = false;
                        rangeChange = 20 * 8;
                        trailLength = 20;
                        trailWidth = 12;
                        trailColor = EUItems.lightninAlloy.color.cpy().a(0.6f);
                        buildingDamageMultiplier = 0.6f;
                    }

                        @Override
                        public void update(Bullet b) {
                            super.update(b);
                            EUFx.normalTrail.at(b.x + Mathf.random(-10, 10), b.y + Mathf.random(-10, 10), 15 * b.fin(), EUItems.lightninAlloy.color.cpy().a(0.6f));
                        }

                        @Override
                        public void updateTrail(Bullet b) {
                            if(!headless && trailLength > 0){
                                if(b.trail == null){
                                    b.trail = new Trail(trailLength);
                                }
                                b.trail.length = 2 + (int) (trailLength * b.fin());
                                b.trail.update(b.x, b.y, trailInterp.apply(b.fin()) * (1f + (trailSinMag > 0 ? Mathf.absin(Time.time, trailSinScl, trailSinMag) : 0f)));
                            }
                        }

                        @Override
                        public void draw(Bullet b) {
                            TextureRegion region = Core.atlas.find(name("mb-mk2"));
                            float ww = 15 * 8 * b.fin(), hh = 15 * 8 * b.fin();
                            Draw.color(EUItems.lightninAlloy.color);
                            //Draw.alpha(b.fout());
                            Draw.rect(region, b.x, b.y, ww, hh, b.rotation() - 90);
                            drawTrail(b);
                        }

                        @Override
                        public void drawTrail(Bullet b) {
                            if(trailLength > 0 && b.trail != null){
                                float z = Draw.z();
                                Draw.z(z - 0.0001f);
                                b.trail.draw(trailColor, 2 + trailWidth * b.fin());
                                Draw.z(z);
                            }
                        }

                        @Override
                        public void createFrags(Bullet b, float x, float y) {
                            fall.create(b, b.x, b.y, b.rotation());
                        }
                    }
            );

            shoot.firstShotDelay = se.lifetime;
            moveWhileCharging = false;
            accurateDelay = false;

            drawer = new DrawMulti(new DrawTurret("reinforced-"), new DrawTrail(2f, EUItems.lightninAlloy.color, 16){{
                y = - 10;
            }});
        }};

        turretResupplyPoint = new TurretResupplyPoint("turret-resupply-point"){{
            requirements(Category.turret, with(Items.graphite, 90, Items.silicon, 180, Items.thorium, 70));
            size = 2;
            hasPower = true;
            consumePower(1);
        }};

        imaginaryReconstructor = new Reconstructor("imaginary-reconstructor"){{
            requirements(Category.units, with(Items.silicon, 3000, Items.graphite, 3500, Items.titanium, 1000, Items.thorium, 800, Items.plastanium, 600, Items.phaseFabric, 350, EUItems.lightninAlloy, 200));
            size = 11;
            upgrades.addAll(
                    new UnitType[]{UnitTypes.reign, EUUnitTypes.suzerain},
                    new UnitType[]{UnitTypes.corvus, EUUnitTypes.nebula},
                    new UnitType[]{UnitTypes.toxopid, EUUnitTypes.asphyxia},
                    new UnitType[]{UnitTypes.eclipse, EUUnitTypes.apocalypse},
                    new UnitType[]{UnitTypes.omura, EUUnitTypes.nihilo},
                    new UnitType[]{UnitTypes.navanax, EUUnitTypes.narwhal}
            );
            researchCostMultiplier = 0.4f;
            buildCostMultiplier = 0.7f;
            constructTime = 60 * 60 * 4.2f;

            consumePower(30f);
            consumeItems(with(Items.silicon, 1200, Items.titanium, 750, Items.plastanium, 450, Items.phaseFabric, 250, EUItems.lightninAlloy, 210));
            consumeLiquid(Liquids.cryofluid, 3.2f);
            liquidCapacity = 192;
        }};
        finalF = new UnitFactory("finalF"){{
            requirements(Category.units, with(EUItems.lightninAlloy, 1200, Items.silicon, 4000, Items.thorium, 2200, Items.phaseFabric, 1500));
            size = 5;
            consumePower(30);
            consumeLiquid(Liquids.water, 1);
            alwaysUnlocked = true;
            config(Integer.class, (UnitFactoryBuild tile, Integer i) -> {
                tile.currentPlan = i < 0 || i >= plans.size ? -1 : i;
                tile.progress = 0;
                tile.payload = null;
            });
            config(UnitType.class, (UnitFactoryBuild tile, UnitType val) -> {
                tile.currentPlan = plans.indexOf(p -> p.unit == val);
                tile.progress = 0;
                tile.payload = null;
            });
            liquidCapacity = 60;
            //buildVisibility = BuildVisibility.sandboxOnly;
        }

            @Override
            public void init() {
                for(int i = 0; i < Vars.content.units().size; i++){
                    UnitType u = Vars.content.unit(i);
                    if(u != null && u.getFirstRequirements() != null){
                        ItemStack[] is = u.getFirstRequirements();
                        ItemStack[] os = new ItemStack[is.length];
                        for (int a = 0; a < is.length; a++) {
                            os[a] = new ItemStack(is[a].item, is[a].amount >= 40 ? (int) (is[a].amount * (1.5 + (hardMod ? 0.5f : 0))) : is[a].amount);
                        }
                        float time = 0;
                        if(u.getFirstRequirements().length > 0) {
                            for (ItemStack itemStack : os) {
                                time += itemStack.amount * itemStack.item.cost;
                            }
                        }
                        if(u.armor < 50) plans.add(new UnitPlan(u, time * 6, os));
                        else plans.add(new UnitPlan(u, time * 2, is));
                    }
                }
                super.init();
            }
        };

        coreKeeper = new CoreKeeper("core-keeper"){{
            requirements(Category.effect, with(EUItems.lightninAlloy, 50, Items.silicon, 400, Items.thorium, 200));
            size = 3;
            health = 1080;
            range = 30;
            consumePower(6);

            alwaysUnlocked = true;

            drawer = new DrawBlock() {
                @Override
                public void draw(Building build) {
                    float x = build.x, y = build.y;

                    Draw.color(build.team.color);
                    Draw.alpha(build.warmup());
                    Draw.z(Layer.effect);
                    for(int i = 0; i < 4; i++){
                        float angle = i * 90;
                        Drawf.tri(x + Angles.trnsx(angle + build.progress() * 2, size * Vars.tilesize/2f * build.warmup()), y + Angles.trnsy(angle + build.progress() * 2, size * Vars.tilesize/2f * build.warmup()), 6, -5, angle + build.progress() * 2);
                        Drawf.tri(x + Angles.trnsx(angle - build.progress() * 3, (size * Vars.tilesize/2f + 3) * build.warmup()), y + Angles.trnsy(angle - build.progress() * 3, (size * Vars.tilesize/2f + 3) * build.warmup()), 4, -3, angle - build.progress() * 3);
                    }

                    if(!(build instanceof CoreKeeperBuild)) return;
                    if(Mathf.equal(build.warmup(), 1, 0.01f)) {
                        Draw.color(build.team.color);
                        Fill.circle(x, y, size * 1.7f);

                        CoreKeeperBuild b = (CoreKeeperBuild) build;
                        float rot = (Time.time * 3) % 360;
                        Tmp.v1.trnsExact(rot, size * 2.7f);
                        float tx = x + Tmp.v1.x, ty = y + Tmp.v1.y/2.2f;
                        if(rot > 50 && rot < 230) Draw.z(Layer.effect - 0.01f);
                        else Draw.z(Layer.effect);
                        b.trail.draw(build.team.color.cpy().mul(EUItems.lightninAlloy.color), size/2f);
                        b.trail.update(tx, ty);
                        Draw.color(build.team.color.cpy().mul(EUItems.lightninAlloy.color));
                        Fill.circle(tx, ty, size / 2f);
                    }

                    Draw.reset();
                }
            };
        }};

        quantumDomain = new Domain("quantum-domain"){{
            requirements(Category.effect, with(EUItems.lightninAlloy, 200, Items.silicon, 500, Items.surgeAlloy, 350, Items.phaseFabric, 300));
            size = 5;
            health = 5000;
            hasPower = true;
            hasItems = false;
            consumePower(9);
            alwaysUnlocked = true;
        }};

        breaker = new Breaker("breaker"){{
            requirements(Category.effect, with(EUItems.lightninAlloy, 5));
            placeableLiquid = true;
            floating = true;

            alwaysUnlocked = true;
        }};


        randomer = new Randomer("randomer"){{
            requirements(Category.distribution, with(Items.silicon, 1));
            alwaysUnlocked = true;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        allNode = new PhaseNode("a-n"){{
            requirements(Category.effect, with(Items.silicon, 1));
            range = 35;
            hasPower = false;
            hasLiquids = true;
            hasItems = true;
            outputsLiquid = true;
            transportTime = 1;
            alwaysUnlocked = true;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        ADC = new ADCPayloadSource("ADC"){{
            requirements(Category.units, with());
            size = 5;
            alwaysUnlocked = true;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};

        guiYsDomain = new Domain("guiYs-domain"){{
            requirements(Category.effect, with());
            size = 2;
            health = 6000;
            buildVisibility = BuildVisibility.sandboxOnly;
            shieldHealth = coolDown = coolDownBk = Float.MAX_VALUE;
            bulletAmount = 0;
            fullEffect = EUFx.shieldDefense;
            canBroken = false;
            range = 30 * 8;
            upSpeed = 5;
            healPercent = healPercentUnit= 25;
            reloadH = reloadHU = 30;
            healByPercent = true;
        }};

        crystalTower = new CrystalTower("crystal-tower"){{
            requirements(Category.effect, with());
            buildVisibility = BuildVisibility.editorOnly;
            size = 3;
        }};

        fireWork = new fireWork("fireWork"){{
            requirements(Category.effect, with(Items.silicon, 10));
            size = 2;
            alwaysUnlocked = true;
            buildVisibility = BuildVisibility.editorOnly;
        }};
    }

    //by guiY for Twilight Fall
//    public static Block ct = new PowerTurret("ct"){{
//        requirements(Category.turret, with(Items.copper, 60, Items.lead, 70, Items.silicon, 60, Items.titanium, 30));
//        range = 25 * 8;
//        recoil = 2f;
//        reload = 80f;
//        shake = 2f;
//        shootEffect = none;
//        smokeEffect = none;
//        size = 2;
//        scaledHealth = 280;
//        targetAir = false;
//        moveWhileCharging = false;
//        accurateDelay = false;
//        shootSound = Sounds.laser;
//        coolant = consumeCoolant(0.2f);
//
//        consumePower(6f);
//
//        //上面炮塔数据随意
//        //下面子弹数据自己改
//        float cont = 60;//扩散角度，1/2值，60 = 120
//        float bRange = range;//范围
//        shootType = new BulletType(){{
//            damage = 100;
//            lifetime = 120;
//            speed = 0;
//            keepVelocity = false;
//            despawnEffect = hitEffect = none;
//
//            hittable = absorbable = reflectable = false;
//        }
//
//            @Override
//            public void update(Bullet b) {
//                //super.update(b);
//                Seq<Healthc> seq = new Seq<>();
//                float r = bRange * (1 - b.foutpow());
//                Vars.indexer.allBuildings(b.x, b.y, r, bd -> {
//                    if(bd.team != b.team && Angles.within(b.rotation(), b.angleTo(bd), cont)) seq.addUnique(bd);
//                });
//                Units.nearbyEnemies(b.team, b.x - r, b.y - r, r * 2, r * 2, u -> {
//                    if(u.type != null && u.type.targetable && b.within(u, r) && Angles.within(b.rotation(), b.angleTo(u), cont)) seq.addUnique(u);
//                });
//                for(int i = 0; i < seq.size; i++){
//                    Healthc hc = seq.get(i);
//                    if(hc != null && !hc.dead()) {
//                        if(!b.hasCollided(hc.id())) {
//                            //伤害的方式在这里改
//
//                            //普攻
//                            hc.damage(damage);
//
//                            //穿甲
//                            //hc.damagePierce(damage);
//
//                            //真伤
//                            //if(hc.health() <= damage) hc.kill();
//                            //else hc.health(hc.health() - damage);
//                            b.collided.add(hc.id());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void draw(Bullet b) {
//                super.draw(b);
//                float pin = (1 - b.foutpow());
//                Lines.stroke(5 * pin, Pal.bulletYellowBack);
//
//                for(float i = b.rotation() - cont; i < b.rotation() + cont; i++){
//                    float lx = EUGet.dx(b.x, bRange * pin, i);
//                    float ly = EUGet.dy(b.y, bRange * pin, i);
//                    Lines.lineAngle(lx, ly, i - 90, bRange/(cont * 2) * pin);
//                    Lines.lineAngle(lx, ly, i + 90, bRange/(cont * 2) * pin);
//                }
//            }
//        };
//    }};
}
