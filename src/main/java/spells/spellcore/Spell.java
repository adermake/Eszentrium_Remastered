package spells.spellcore;

import esze.analytics.SaveUtils;
import esze.enums.GameType;
import esze.enums.Gamestate;
import esze.main.main;
import esze.types.TypeTEAMS;
import esze.utils.*;
import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import spells.spells.AntlitzderGoettin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public abstract class Spell {

    //FINAL
    private final double minDist = 0.0001;

    //TODO
    //Not hit custom spawned entitys
    //Gravity

    //STATS

    protected String name;
    protected int cooldown;
    public Player caster;
    public Location loc;

    //CODEVARS


    protected int steprange = 0;
    protected double step = 0;
    protected int casttime = 0;
    protected int cast = 0;
    protected double powerlevel = 1;
    protected double speed = 1;
    protected double speedmultiplier = 1;
    protected double hitboxSize = 1;
    public int spellkey = -1;


    protected boolean refined = false;
    protected boolean hitEntity = true;
    protected boolean hitPlayer = true;
    protected boolean hitSpell = true;
    protected boolean hitBlock = true;
    protected boolean canHitSelf = false;
    protected boolean canHitCastersSpells = false;
    protected boolean canBeSilenced = true;
    protected boolean autocancel = false;
    protected boolean multihit = false;
    protected boolean dieOnLowPower = true;
    protected boolean powerBattle = false;
    protected boolean traitorSpell = false;
    protected boolean dead = false;
    protected boolean silencable = false;
    protected boolean tagPlayer = true; //hit Players will get a damage Cause
    protected boolean lazySpell = false;

    //VARS

    protected boolean spellLoopStarted = false;
    public ArrayList<Entity> noTargetEntitys = new ArrayList<Entity>();
    public ArrayList<Entity> hitEntitys = new ArrayList<Entity>();
    public static ArrayList<Entity> pressingF = new ArrayList<Entity>();
    public static ArrayList<Entity> clearpressingF = new ArrayList<Entity>();
    public static ArrayList<Spell> spell = new ArrayList<Spell>();
    protected static ArrayList<Player> gliding = new ArrayList<Player>();
    protected static ArrayList<Player> hasDied = new ArrayList<Player>();
    protected static ArrayList<Player> hasDiedEntry = new ArrayList<Player>();
    public static ArrayList<Entity> unHittable = new ArrayList<Entity>();
    protected Location startPos;
    protected Location lastAirPos;
    protected Player originalCaster;
    protected ArrayList<SpellType> spellTypes = new ArrayList<SpellType>();
    protected ArrayList<String> lore = new ArrayList<String>();
    protected ArrayList<String> betterlore = new ArrayList<String>();
    protected ArrayList<Block> localPhantomBlock = new ArrayList<Block>();

    //FLAGS

    //EVENTS
    public static ArrayList<Spell> hitSpellEvent = new ArrayList<Spell>();
    public static ArrayList<Spell> takeDamageEvent = new ArrayList<Spell>();
    //CC
    protected static ArrayList<Block> phantomBlock = new ArrayList<Block>();
    public static HashMap<Player, SilenceSelection> silenced = new HashMap<Player, SilenceSelection>();
    public static HashMap<Player, DamageCauseContainer> damageCause = new HashMap<Player, DamageCauseContainer>();
    //

    public ArrayList<SpellType> getSpellTypes() {
        return spellTypes;

    }

    public static void clearSpells() {
        for (Spell spell : spell) {
            spell.dead = true;
        }
    }


    public boolean castSpell(Player p, String name) {

        this.name = name;

        originalCaster = p;

        if (checkSilence()) {

            Actionbar bar = new Actionbar("bist verstummt!");
            bar.send(p);

            return true;
        }

        return createdSpell(p);
    }

    public boolean refund = false;

    public boolean createdSpell(Player p) {
        applySpellKey(p);
        spell.add(this); //XXXX
        caster = p;
        loc = p.getEyeLocation();
        startPos = p.getEyeLocation();
        lastAirPos = p.getEyeLocation();
        setUp(); // steht wahrscheinlich an der falschen Stelle
        swap();
        if (casttime > 0) {
            startCastLoop();
        } else {
            launch();
            startSpellLoop();
        }
        if (refund) {
            Actionbar a = new Actionbar("Kein Ziel gefunden!");
            a.send(caster);
        }
        return refund;
    }


    public void startCastLoop() {

        new BukkitRunnable() {

            public void run() {

                cast++;
                if (canBeSilenced && checkSilence()) {
                    this.cancel();
                }

                if (caster.getGameMode() == GameMode.ADVENTURE) {
                    this.cancel();
                    return;
                }
                cast();

                if (cast >= casttime) {
                    //loc = caster.getEyeLocation();

                    launch();
                    startSpellLoop();
                    this.cancel();
                }
            }
        }.runTaskTimer(main.plugin, 1, 1);
    }

    public boolean checkSilence() {


        if (silenced.containsKey(originalCaster)) {

            return Spell.silenced.get(originalCaster).filter(spellTypes);
        }

        return false;

    }

    public void disableEntityHitbox(Entity ent) {
        //THIS DOES NO LONGER WORK
        //FIX SOMEHOW?

    }


    public static void silence(Player p, SilenceSelection s) {
        silenced.put(p, s);

    }
    // BOUND SPELLS

    boolean bound = false;
    public boolean boundHitGround = false;
    public boolean boundOnGround = false;
    public Entity spellEnt;

    public void checkSpellEnt() {
        if (spellEnt != null) {


            if (!spellEnt.isValid() && bound) {
                dead = true;
                spell.remove(this);
                onDeath();

            }
        }
    }

    public void checkIfHitGround() {

        boundOnGround = spellEnt.isOnGround();

        if (boundOnGround && !boundHitGround) {
            boundHitGround = true;
            onBlockHit(spellEnt.getLocation().getBlock());
        }
        if (!boundOnGround) {
            boundHitGround = false;
        }


    }

    public void startSpellLoop() {


        if (!spellLoopStarted) {


            //TickLoop

            new BukkitRunnable() {
                public void run() {


                    display();
                    if (bound) {
                        checkIfHitGround();
                        checkSpellEnt();
                    }


                    if (dead == true) {
                        spell.remove(this);
                        this.cancel();
                    }
                }
            }.runTaskTimer(main.plugin, 1, 1);


            //MoveLoop

            final Spell sp = this;
            new BukkitRunnable() {
                int ts = 0;

                public void run() {

                    ts++;

                    if (silencable) {
                        if (silenced.containsKey(caster)) {
                            if (silenced.get(caster).filter(spellTypes)) {
                                dead = true;
                            }
                        }

                    }
                    if (autocancel) {
                        if (caster.getGameMode() == GameMode.ADVENTURE) {
                            dead = true;
                        }
                    }
					/*
					if (spellTypes.contains(SpellType.SELFCAST) && (caster.getGameMode() == GameMode.ADVENTURE || isSilenced())) {
						dead = true;
						this.cancel();
						return;
					}
					*/
                    if (ts >= 1 / speed) {
                        ts = 0;

                        for (int i = 0; i < speed; i++) {
                            if (speedmultiplier != 0) {
                                move();
                                display();
                                if (loc != null)
                                    lastAirPos = loc.clone();


                            }

                            if (!dead && !lazySpell) {
                                hasDied = new ArrayList<Player>(hasDiedEntry);
                                collideWithPlayer();
                                collideWithEntity();
                                collideWithSpell();
                                collideWithBlock();


                            }

                            if (hasDied.contains(caster)) {
                                dead = true; //EXPERIMENTAL
                            }
                            if (dead) {


                                spell.remove(sp);
                                this.cancel();
                                break;
                            }
                            step++;
                            if (steprange > 0 && step >= steprange) {
                                dead = true;

                            }


                        }


                    }
                    if (dead == true) {


                        onDeath();
                        spell.remove(sp);
                        clearPhantomBlocks();
                        this.cancel();
                    }

                    pressingF.clear();

                }
            }.runTaskTimer(main.plugin, 1, 1);


        }
        spellLoopStarted = true;
    }


    public void callCollision(double hitbox) {

        hitboxSize = hitbox;
        collideWithPlayer();
        collideWithEntity();
    }


    public void collideWithPlayer() {
        if (hitPlayer) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p == caster && !canHitSelf) {
                    continue;
                }
                if (unHittable.contains(p)) {
                    continue;
                }
                if (multihit == false && hitEntitys.contains(p)) {
                    continue;
                }
                if (p.getGameMode().equals(GameMode.ADVENTURE)) {
                    continue;
                }


                if (p.getEyeLocation().distance(loc) < 0.6 + hitboxSize || p.getLocation().distance(loc) < 0.6 + hitboxSize) {
                    hitEntitys.add(p);

                    if (spellHitPlayerEvent(p)) {
                        continue;
                    }
                    onPlayerHit(p);
                    //tags Players
                    if (tagPlayer) {
                        tagPlayer(p);
                    }
                }
            }
        }
    }


    public void collideWithEntity() {
        if (hitEntity) {
            for (LivingEntity ent : Bukkit.getWorld("world").getLivingEntities()) {
                if (unHittable.contains(ent)) {
                    continue;
                }
                if (ent instanceof Player || ent instanceof Cow || ent instanceof ArmorStand) {
                    continue;
                }

                if (multihit == false && hitEntitys.contains(ent)) {
                    continue;
                }
                if (noTargetEntitys.contains(ent))
                    continue;

                if (ent.getEyeLocation().distance(loc) < 0.6 + hitboxSize || ent.getLocation().distance(loc) < 0.6 + hitboxSize) {
                    hitEntitys.add(ent);
                    onEntityHit(ent);
                }
            }
        }
    }


    public void collideWithSpell() {
        if (hitSpell) {
            for (Spell spell : spell) {
                if (spell.equals(this)) {
                    continue;
                }
                if ((spell.caster == caster && !canHitCastersSpells) || spell.dead || !spell.hitSpell)
                    continue;
                if (spell.getLocation().distance(loc) < hitboxSize + spell.hitboxSize) {
                    if (powerBattle && spell.powerBattle) {
                        double d = spell.powerlevel;
                        spell.powerlevel -= powerlevel;
                        powerlevel -= d;
                        if (spell.powerlevel <= 0 && spell.dieOnLowPower) {
                            spell.dead = true;
                        }
                        if (powerlevel <= 0 && dieOnLowPower) {
                            dead = true;

                        }
                    }
                    onSpellHit(spell);
                    spell.onSpellHit(this);
                }
            }
        }

    }

    public void collideWithBlock() {
        if (hitBlock) {
            if (loc.getBlock().getType().isSolid() && !loc.getBlock().getType().toString().contains("DOOR")) {
                onBlockHit(loc.getBlock());

            }
            if (phantomBlock.contains(loc.getBlock())) {
                onBlockHit(loc.getBlock());
            }
        }
    }

    //GETTER

    public Location getLocation() {
        return loc;
    }

    public Vector getDirection() {
        return loc.getDirection();
    }

    //MEHTHODS


    public void clearPhantomBlocks() {
        for (Block b : localPhantomBlock) {
            if (b != null)
                removePhantomblock(b);
        }
        localPhantomBlock.clear();
    }

    public static int randInt(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;
    }

    public static double randDouble(double min, double max) {
        double randomNum = ThreadLocalRandom.current().nextDouble(min, max);
        return randomNum;
    }


    public static Vector randVector() {
        int ix = randInt(-100, 100);
        int iy = randInt(-100, 100);
        int iz = randInt(-100, 100);
        double dx = ((double) ix) / 100;
        double dy = ((double) iy) / 100;
        double dz = ((double) iz) / 100;
        Vector v = new Vector(dx, dy, dz);
        return v;

    }

    public void playSound(Sound s, Location loc, double volume, double pitch) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(loc, s, (float) volume, (float) pitch);
        }
    }

    public void playGlobalSound(Sound s, double volume, double pitch) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), s, (float) volume, (float) pitch);
        }
    }

    public void playSingleSound(Sound s, Player p, double volume, double pitch) {
        p.playSound(p.getLocation(), s, (float) volume, (float) pitch);
    }

    public void clearHitBlacklist() {
        hitEntitys.clear();
    }

    public BlockFace bounce0() {


        Location l = lastAirPos;
        Vector v = loc.toVector().subtract(l.toVector());
        if (v.length() > 1) {
            v = v.normalize();
        }

        double stepSize = 0.1;

        int i = 100;
        while (!l.getBlock().getType().isSolid()) {
            i--;

            l.add(v.clone().multiply(stepSize));

            if (i <= 0) {
                Bukkit.broadcastMessage("Bounce mal den ram weg");
                break;

            }

        }

        Location blockLock = l.getBlock().getLocation().add(0.5, 0.5, 0.5);
        double dist = 10000000;
        BlockFace nearestFace = null;
        for (BlockFace bf : BlockFace.values()) {
            if (bf == BlockFace.DOWN || bf == BlockFace.UP || bf == BlockFace.WEST || bf == BlockFace.SOUTH || bf == BlockFace.EAST || bf == BlockFace.NORTH) {
                Vector bfV = bf.getDirection().normalize().multiply(0.5);

                if (blockLock.clone().add(bfV).distance(l) < dist) {
                    nearestFace = bf;
                    dist = blockLock.clone().add(bfV).distance(l);
                }

            }
        }
        Vector d = nearestFace.getDirection();

        if (nearestFace == BlockFace.NORTH || nearestFace == BlockFace.SOUTH) {
            d.setZ(-d.getZ());

        }
        if (nearestFace == BlockFace.UP) {
            d.setY(Math.abs(d.getY()));


        }
        if (nearestFace == BlockFace.DOWN) {
            d.setY(-Math.abs(d.getY()));


        }
        if (nearestFace == BlockFace.EAST || nearestFace == BlockFace.WEST) {
            d.setX(-d.getX());


        }


        loc.setDirection(d);
        return nearestFace;
    }

    public BlockFace bounce() {

        if (loc.getBlock().getType().isSolid()) {

            Vector dir = loc.getDirection();
            double minDist = 1000000;
            BlockFace nearestFace = null;
            for (BlockFace bf : BlockFace.values()) {
                if (bf == BlockFace.DOWN || bf == BlockFace.UP || bf == BlockFace.WEST || bf == BlockFace.SOUTH || bf == BlockFace.EAST || bf == BlockFace.NORTH) {


                    Block test = loc.getBlock().getRelative(bf);
                    if (test.getType().isSolid()) {
                        continue;
                    }
                    double dist = test.getLocation().clone().add(0.5, 0.5, 0.5).distance(startPos);
                    if (dist < minDist) {
                        minDist = dist;
                        nearestFace = bf;
                    }
                }

            }
            if (nearestFace == BlockFace.NORTH || nearestFace == BlockFace.SOUTH) {
                dir.setZ(-dir.getZ());

            }
            if (nearestFace == BlockFace.UP) {
                dir.setY(Math.abs(dir.getY()));


            }
            if (nearestFace == BlockFace.DOWN) {
                dir.setY(-Math.abs(dir.getY()));


            }
            if (nearestFace == BlockFace.EAST || nearestFace == BlockFace.WEST) {
                dir.setX(-dir.getX());


            }
            int i = 0;


            //playSound(Sound.BLOCK_ANVIL_LAND, loc, 1, 2);
            startPos = loc.clone();
            loc.setDirection(dir);
            return nearestFace;
        }
        return null;
    }

    public void bounceDir() {
		/*
		if (loc.getBlock().getType().isSolid()) {
			
			Vector dir = loc.getDirection();
			double minDist = 1000000;
			BlockFace nearestFace = null;
			for (BlockFace bf : BlockFace.values()) {
				if (bf == BlockFace.DOWN || bf == BlockFace.UP || bf == BlockFace.WEST || bf == BlockFace.SOUTH || bf == BlockFace.EAST || bf == BlockFace.NORTH) {
					
				
				Block test = loc.getBlock().getRelative(bf);
				if (test.getType().isSolid()) {
					continue;
				}
				double dist = test.getLocation().clone().add(0.5,0.5,0.5).distance(startPos);
				if (dist < minDist) {
					minDist = dist;	
					nearestFace = bf;
				}
				}
				
			}
			if (nearestFace == BlockFace.NORTH || nearestFace == BlockFace.SOUTH) {
				dir.setZ(-dir.getZ());
				while (loc.getBlock().getType() == Material.AIR) {
					loc.add(dir.getX(), dir.getY(), dir.getZ());
				}
				//playSound(Sound.BLOCK_ANVIL_LAND, loc, 0.1F, 2);
				startPos = loc.clone();
				
			}
			if (nearestFace == BlockFace.UP || nearestFace == BlockFace.DOWN) {
				dir.setY(-dir.getY());
				while (loc.getBlock().getType() == Material.AIR) {
					loc.add(dir.getX(), dir.getY(), dir.getZ());
				}
				//playSound(Sound.BLOCK_ANVIL_LAND, loc, 1, 2);
				startPos = loc.clone();
			}
			if (nearestFace == BlockFace.EAST || nearestFace == BlockFace.WEST) {
				dir.setX(-dir.getX());
				while (loc.getBlock().getType() == Material.AIR) {
					loc.add(dir.getX(), dir.getY(), dir.getZ());
				}
				//playSound(Sound.BLOCK_ANVIL_LAND, loc, 1, 2);
				startPos = loc.clone();
				
			}
			
			return dir;
		}
		return null;*/
    }

    public void slideDir(Location l) {
		/*
		ArrayList<BlockFace> bfs = new ArrayList<BlockFace>();
		
		
		
			for (BlockFace nearestFace: BlockFace.values()) {
			
			if (l.getBlock().getRelative(nearestFace).getType().isSolid()) {
					bfs.add(nearestFace);
				}
			}
			
		
		return bfs;
		*/
    }

    public Entity spawnEntity(EntityType et) {
        return loc.getWorld().spawnEntity(loc, et);
    }

    public Entity spawnEntity(EntityType et, Location loca) {
        return (Entity) loc.getWorld().spawnEntity(loca, et);
    }

    public Entity spawnEntity(EntityType et, Location loca, int ticks) {
        final Entity ent = loc.getWorld().spawnEntity(loca, et);
        new BukkitRunnable() {
            public void run() {
                ent.remove();
            }
        }.runTaskLater(main.plugin, ticks);
        return ent;
    }

    public void damage(Entity ent, double damage, Player damager) {

        if (GameType.getType() instanceof TypeTEAMS) {
            if (Gamestate.getGameState() == Gamestate.LOBBY) {
                return;
            }
            if (ent instanceof Player) {
                TypeTEAMS team = (TypeTEAMS) GameType.getType();
                Player victim = (Player) ent;
                if (team.getTeammates(damager).contains(victim)) {
                    return;
                }
            }


        }


        if (ent instanceof Player) {
            tagPlayer((Player) ent);
        }
        if (ent instanceof LivingEntity) {
            ((LivingEntity) ent).damage(damage);
        }
        //Damage will be an Damage cause even when tagPlayers has been disabled
    }

    public void tagPlayer(Player ent) {


        //RALFS RICHTIGER DAMAGE CAUSE
        damageCause.remove(ent);
        damageCause.put(ent, new DamageCauseContainer(caster, name));

    }

    public void healAll(LivingEntity ent, double damage, Player healer) {

        double newhealth = ent.getHealth() + damage;

        if (ent.getMaxHealth() < newhealth) {
            ent.setHealth(ent.getMaxHealth());
        } else {
            ent.setHealth(newhealth);
        }


    }

    public void heal(LivingEntity ent, double damage, Player healer) {

        if (GameType.getType() instanceof TypeTEAMS) {
            if (ent instanceof Player) {
                TypeTEAMS team = (TypeTEAMS) GameType.getType();
                Player victim = (Player) ent;
                if (!team.getTeammates(caster).contains(victim)) {
                    return;
                }
            }


        }
        double newhealth = ent.getHealth() + damage;

        if (ent.getMaxHealth() < newhealth) {
            ent.setHealth(ent.getMaxHealth());
        } else {
            ent.setHealth(newhealth);
        }


    }

    public boolean isSpellType(SpellType st) {
        return spellTypes.contains(st);
    }

    public void addSpellType(SpellType st) {
        spellTypes.add(st);
    }

    public double calcLerpFactor(double s, double sr) {
        double dstep = s;
        double dsteprange = sr;

        return dstep / dsteprange;
    }

    public void bindEntity(Entity e) {
        noTargetEntitys.add(e);
        spellEnt = e;
        bound = true;
    }

    public void doKnockback(Entity e, Location fromLocation, double speed) {
        // multiply default 0.25
        if (e instanceof Player) {
            if (e != caster) {
                tagPlayer((Player) e);
            }
        }
        if (fromLocation.toVector().distance(e.getLocation().toVector()) > minDist) {
            e.setVelocity(fromLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(-speed));
        }
    }


    public Vector doPull(Entity e, Location toLocation, double speed) {
        // multiply default 0.25
        if (e instanceof Player) {
            if (e != caster) {
                tagPlayer((Player) e);
            }
        }
        if (toLocation.toVector().distance(e.getLocation().toVector()) > minDist) {
            e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(speed));
            return toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(speed);
        }

        return new Vector(0, 0, 0);
    }

    public Vector doPullin(Entity e, Location toLocation, double pspeed, double speed) {
        if (e.getLocation().distance(toLocation) < 30) {
            return doPin(e, toLocation, pspeed);
        } else {
            return doPull(e, toLocation, speed);
        }


    }

    public Vector doPin(Entity e, Location toLocation) {
        // multiply default 0.25
        if (e instanceof Player) {
            if (e != caster) {
                tagPlayer((Player) e);
            }
        }
        if (toLocation.toVector().distance(e.getLocation().toVector()) > minDist) {
            double s = e.getLocation().distance(toLocation) / 5;
            e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s));
            return toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s);
        }

        return new Vector(0, 0, 0);
    }

    public Vector doPin(Entity e, Location toLocation, double power) {
        // multiply default 0.25
        if (e instanceof Player) {
            if (e != caster) {
                tagPlayer((Player) e);
            }
        }
        if (toLocation.toVector().distance(e.getLocation().toVector()) > minDist) {
            double s = e.getLocation().distance(toLocation) / 5;
            e.setVelocity(toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s * power));
            return toLocation.toVector().subtract(e.getLocation().toVector()).normalize().multiply(s);
        }

        return new Vector(0, 0, 0);
    }

    public Player pointEntity(Player p) {
        int range = 300;
        int toleranz = 3;
        Location loc = p.getLocation();
        for (double t = 1; t <= range; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            // Particel


            if (loc.getBlock().getType().isSolid()) {

                break;
            }

            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!(pl.getName().equalsIgnoreCase(p.getName())) && pl.getGameMode() != GameMode.ADVENTURE) {

                    Location ploc1 = pl.getLocation();
                    Location ploc2 = pl.getLocation();
                    ploc2.add(0, 1, 0);
                    if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {


                        return pl;
                    }
                }
            }

            // SUBTRACTING LOCATION UM den prozess
            // von vorne zu
            // starten
            loc.subtract(x, y, z);
        }
        return null;

    }

    public Spell pointSpell(Player p) {
        int range = 300;
        int toleranz = 3;
        Location loc = p.getLocation();
        for (double t = 1; t <= range; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            // Particel


            if (loc.getBlock().getType().isSolid()) {

                break;
            }

            for (Spell sp : spell) {
                if (sp.hitSpell) {

                    Location ploc1 = sp.getLocation();

                    if (ploc1.distance(loc) <= sp.hitboxSize + toleranz) {


                        return sp;
                    }
                }
            }

            // SUBTRACTING LOCATION UM den prozess
            // von vorne zu
            // starten
            loc.subtract(x, y, z);
        }
        return null;

    }

    public Player pointEntityCone(Location start, Player p, boolean tarteammates) {
        int range = 300;
        int toleranz = 3;
        Location loc = start.clone();
        for (double t = 2; t <= range; t = t + 0.5) {
            toleranz += 0.25F;
            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            // Particel


            if (loc.getBlock().getType().isSolid()) {

                break;
            }

            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!tarteammates && isOnTeam(pl)) {
                    continue;
                }
                if (pl != p && pl.getGameMode() != GameMode.ADVENTURE) {

                    Location ploc1 = pl.getLocation();
                    Location ploc2 = pl.getLocation();
                    ploc2.add(0, 1, 0);
                    if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {


                        return pl;
                    }
                }
            }

            // SUBTRACTING LOCATION UM den prozess
            // von vorne zu
            // starten
            loc.subtract(x, y, z);
        }
        return null;

    }

    public Entity pointRealEntity(Player p) {
        int range = 200;
        int toleranz = 3;
        Location loc = p.getLocation();
        for (double t = 1; t <= range; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            // Particel


            if (loc.getBlock().getType().isSolid()) {

                break;
            }

            for (Entity ent : p.getWorld().getEntities()) {

                if (ent instanceof Player) {
                    if (ent == p || ((Player) ent).getGameMode() == GameMode.ADVENTURE) {
                        continue;
                    }
                }


                Location ploc1 = ent.getLocation();
                Location ploc2 = ent.getLocation();
                ploc2.add(0, 1, 0);
                if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {


                    return ent;
                }

            }

            // SUBTRACTING LOCATION UM den prozess
            // von vorne zu
            // starten
            loc.subtract(x, y, z);
        }
        return null;

    }


    public Player pointEntity(Player p, int range) {

        int toleranz = 1;
        Location loc = p.getLocation();
        for (double t = 1; t <= range; t = t + 0.5) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            // Particel


            if (loc.getBlock().getType().isSolid()) {

                break;
            }

            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!(pl.getName().equalsIgnoreCase(p.getName())) && pl.getGameMode() != GameMode.ADVENTURE) {

                    Location ploc1 = pl.getLocation();
                    Location ploc2 = pl.getLocation();
                    ploc2.add(0, 1, 0);
                    if (ploc1.distance(loc) <= toleranz || ploc2.distance(loc) <= toleranz) {


                        return pl;
                    }
                }
            }

            // SUBTRACTING LOCATION UM den prozess
            // von vorne zu
            // starten
            loc.subtract(x, y, z);
        }
        return null;

    }

    public boolean isOnTeam(Player p) {


        if (GameType.getType() instanceof TypeTEAMS) {
            TypeTEAMS teams = (TypeTEAMS) GameType.getType();

            if (teams.getTeammates(p) == null || teams.getTeammates(p).size() <= 0) {
                return false;
            }
            if (teams.getTeammates(p).contains(caster)) {
                return true;
            }


        }


        return false;

    }


    public void spawnPhantomblock(Block b, Material m) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendBlockChange(b.getLocation(), Bukkit.createBlockData(m));
        }
        phantomBlock.add(b);
        localPhantomBlock.add(b);
    }

    public void removePhantomblock(Block c) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendBlockChange(c.getLocation(), c.getBlockData());
        }
        phantomBlock.remove(c);
    }


    public Location block(Player p) {
        Location loc = p.getEyeLocation();
        for (int t = 1; t <= 300; t++) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t;
            double z = direction.getZ() * t;

            loc.add(x, y, z);
            Location lo = loc.clone();

            if (loc.getY() <= 60) {
                return null;
            }
            if (loc.getY() > 250) {
                return null;
            }
            if (loc.getBlock().getType() != Material.AIR) {
                return loc;

            }

            loc.subtract(x, y, z);
        }
        return null;

    }


    public Location preblock(Player p) {
        Location loc = p.getEyeLocation();
        for (int t = 1; t <= 300; t++) {

            Vector direction = loc.getDirection().normalize();
            double x = direction.getX() * t;
            double y = direction.getY() * t;
            double z = direction.getZ() * t;

            loc.add(x, y, z);
            Location lo = loc.clone();

            if (loc.getY() <= 60) {
                return null;
            }

            if (loc.getBlock().getType() != Material.AIR) {
                return loc.add(direction.multiply(-1));

            }

            loc.subtract(x, y, z);
        }
        return null;

    }

    public Location block(Player p, int range) {
        Location loc = p.getEyeLocation();
        for (int t = 1; t <= range * 2; t++) {

            Vector direction = loc.getDirection().normalize().multiply(0.5);
            double x = direction.getX() * t;
            double y = direction.getY() * t;
            double z = direction.getZ() * t;
            loc.add(x, y, z);
            Location lo = loc.clone();

            if (loc.getBlock().getType() != Material.AIR) {
                return loc;

            }

            loc.subtract(x, y, z);
        }
        return null;

    }

    public Location loc(Player p, double range) {
        Location loc = p.getLocation();
        Location ret = p.getLocation();
        for (int t = 1; t <= range; t++) {

            Vector direction = loc.getDirection().normalize().multiply(0.5);
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);


            ret = loc.clone();
            loc.subtract(x, y, z);
        }
        return ret;

    }

    public Location loc(Player p, int range) {
        Location loc = p.getLocation();
        Location ret = p.getLocation();
        for (int t = 1; t <= range; t++) {

            Vector direction = loc.getDirection().normalize().multiply(0.5);
            double x = direction.getX() * t;
            double y = direction.getY() * t + 1.5;
            double z = direction.getZ() * t;
            loc.add(x, y, z);


            ret = loc.clone();
            loc.subtract(x, y, z);
        }
        return ret;

    }

    public void indicatorLine(Location l1, Color c) {

        Location l = preblock(caster);
        if (l != null) {

            ParUtils.parLineRedstone(l1.clone(), l, c, 1, 1F, caster);
        }


    }


    public Location getTop(Location loca) {

        while (loca.getBlock().getType().isSolid()) {
            loca.add(0, 1, 0);
        }
        return loca.getBlock().getLocation().add(0.5, 0.5, 0.5);

    }

    public Location getFloor(Location loca) {

        while (!loca.getBlock().getType().isSolid()) {
            loca.add(0, -1, 0);
        }
        return loca.getBlock().getLocation().add(0.5, 0.5, 0.5);

    }

    public Location getCieling(Location loca) {


        while (!loca.getBlock().getType().isSolid()) {
            loca.add(0, 1, 0);
        }
        return loca.getBlock().getLocation().add(0.5, 0.5, 0.5);

    }

    public Location getFloor(Location loca, int cap) {
        Location ori = loca.clone();
        int t = 0;
        while (!loca.getBlock().getType().isSolid()) {
            loca.add(0, -1, 0);
            t++;
            if (t >= cap) {
                return ori;
            }
        }
        Location retLoc = loca.getBlock().getLocation();
        retLoc.setX(loca.getX());
        retLoc.setZ(loca.getZ());
        return retLoc;

    }

    public Location getCieling(Location loca, int cap) {
        Location ori = loca.clone();
        int t = 0;
        while (!loca.getBlock().getType().isSolid()) {
            t++;
            if (t >= cap) {
                return ori;
            }
            loca.add(0, 1, 0);


        }
        Location retLoc = loca.getBlock().getLocation();
        retLoc.setX(loca.getX());
        retLoc.setZ(loca.getZ());
        return retLoc;

    }

    public String getName() {
        return name;
    }

    public void setGliding(Player p, boolean glide) {
        p.setGliding(glide);
        if (glide) {
            gliding.add(p);
        } else {
            gliding.remove(p);
        }


    }

    public boolean checkHit(LivingEntity le, Location loc, Entity p, double range) {
        if (AntlitzderGoettin.deflect.contains(le)) {

            return false;
        }
        if (le instanceof Player) {


            if (((Player) le).getGameMode() == GameMode.ADVENTURE) {
                return false;
            }

        }
        if (le != p) {

            if (le instanceof Cow || le instanceof ArmorStand)
                return false;

            if (le.getLocation().distance(loc) < range || le.getEyeLocation().distance(loc) < range) {
                return true;
            }

        }


        return false;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public ArrayList<String> getBetterLore() {
        return betterlore;
    }

    public void setLore(String ls) {
        lore.clear();
        ls = formatLore(ls);
        for (String s : ls.split("#")) {
            lore.add(s);

        }

        lore.add(" ");
        lore.add("§eCooldown: §7" + cooldown / 20 + "s");
    }

    public void setBetterLore(String ls) {
        betterlore.clear();
        ls = formatLore(ls);
        for (String s : ls.split("#")) {
            betterlore.add(s);

        }

        betterlore.add(" ");
        betterlore.add("§eCooldown: §7" + cooldown / 20 + "s");
    }

    public boolean isDead() {
        return dead;
    }

    public String formatLore(String lore) {


        lore = lore.replace("# #", " ");
        lore = lore.replace("#", " ");
        lore = lore.replace("§7", "");
        lore = lore.replace("§e", "");


        lore = "§7" + lore;
        String spl = "";
        //lore = lore.replace("F:", "# #
        //lore = lore.replace("Shift:", "# #

        String m1 = "";
        String f2 = "";
        String shift3 = "";
        if (lore.contains("F:") && lore.contains("Shift")) {
            m1 = lore.split("F:")[0]; //base + shift
            String f3 = lore.split("F:")[1];
            f2 = m1.split("Shift:")[0]; // base
            shift3 = m1.split("Shift:")[1];// shift

            spl = formatTab(f2) + formatTab("# #§eShift:§7" + shift3) + formatTab("# #§eF:§7" + f3);
        } else if (lore.contains("F:")) {
            m1 = lore.split("F:")[0];
            f2 = lore.split("F:")[1];
            spl = formatTab(m1) + formatTab("# #§eF:§7" + f2);
        } else if (lore.contains("Shift:")) {
            m1 = lore.split("Shift:")[0];
            shift3 = lore.split("Shift:")[1];
            spl = formatTab(m1) + formatTab("# #§eShift:§7" + shift3);
        } else {
            m1 = lore;
            spl = formatTab(m1);
        }


        return spl;


    }

    public String formatTab(String tab) {
        int spaceSlot = 0;
        String spl = tab;
        int m = 0;
        for (int c = 0; c < spl.length(); c++) {
            if (spl.charAt(c) == ' ') {
                spaceSlot = c;

            }

            if (m % 30 == 0 && m != 0) {

                spl = spl.substring(0, spaceSlot) + "#§7" + spl.substring(spaceSlot + 1, spl.length());
                c += 3;
                spaceSlot = 0;
            }
            m++;

        }
        return spl;
    }

    public void kill() {
        dead = true;
    }

    public void instaKill() {
        dead = true;
        onDeath();
        spell.remove(this);
        clearPhantomBlocks();
    }

    public boolean swap() {


        if (EventCollector.quickSwap.contains(caster)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (EventCollector.quickSwap.contains(caster)) {
                        EventCollector.quickSwap.remove(caster);
                    }
                }
            }.runTaskLater(main.plugin, 1);
            return true;

        }

        return false;
    }

    public boolean swap(Player p) {


        if (EventCollector.quickSwap.contains(p)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (EventCollector.quickSwap.contains(p)) {
                        EventCollector.quickSwap.remove(p);
                    }
                }
            }.runTaskLater(main.plugin, 1);
            return true;

        }

        return false;
    }

    public void clearswap() {


        if (EventCollector.quickSwap.contains(caster)) {
            EventCollector.quickSwap.remove(caster);
        }

    }

    public void clearswap(Player p) {


        if (EventCollector.quickSwap.contains(p)) {
            EventCollector.quickSwap.remove(p);
        }

    }

    public static Location lookAt(Location loc, Location lookat) {

        loc = loc.clone();

        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        if (dx != 0) {

            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        loc.setPitch((float) -Math.atan(dy / dxz));

        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }

    public static Player getNearestPlayer(Player c) {
        double distance = 10000;
        Player nearest = c;
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.getGameMode() != GameMode.SURVIVAL) {
                continue;
            }
            if (nearest == c) {
                nearest = p;
                distance = c.getLocation().distance(p.getLocation());
            }
            if (p != c && p.getGameMode() != GameMode.ADVENTURE) {
                double dis = c.getLocation().distance(p.getLocation());
                if (dis < distance) {
                    nearest = p;
                    distance = dis;
                }
            }
        }


        return nearest;

    }

    public static Player getNearestPlayer(Player c, Location l, double range) {
        double distance = 10000;
        Player nearest = null;
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p != c && p.getGameMode() != GameMode.ADVENTURE && !GameType.getType().spectator.contains(p)) {
                double dis = l.distance(p.getLocation());
                if (dis < distance && dis < range) {
                    nearest = p;
                    distance = dis;
                }
            }
        }


        return nearest;

    }

    public static LivingEntity getNearestEntity(Player c, Location l, double range) {
        double distance = 10000;
        LivingEntity nearest = null;
        for (LivingEntity p : c.getWorld().getLivingEntities()) {

            if (unHittable.contains(p)) {
                continue;
            }
            if (p instanceof Player) {
                Player player = (Player) p;

                if (player.getGameMode() == GameMode.ADVENTURE) {
                    continue;
                }


            }
            if (p != c) {
                double dis = l.distance(p.getLocation());
                if (dis < distance && dis < range) {
                    nearest = p;
                    distance = dis;
                }
            }
        }


        return nearest;

    }

    public FallingBlock spawnFallingBlock(Location l, Material m) {
        return (FallingBlock) loc.getWorld().spawnFallingBlock(l, m, (byte) 0);
    }

    public static ArrayList<Block> multiselect(Location l, Vector v, int height, int width) {
        ArrayList<Block> ab = new ArrayList<Block>();
        Location loc = l.clone();
        loc.setDirection(v);
        for (double i = -height / 2; i <= height / 2; i += 1) {
            for (double j = -width / 2; j <= width / 2; j += 1) {

                Vector grid = new Vector(i, 0, j);
                Matrix.rotateMatrixVectorFunktion(grid, loc.clone());
                loc.add(grid);
                ab.add(loc.getBlock());

                loc.subtract(grid);
            }


        }
        return ab;


    }

    public Vector slerp(Vector start, Vector end, double percent) {
        Vector s = start.clone();
        Vector e = end.clone();

        double dot = s.dot(e);

        clamp(dot, -1.0, 1.0);

        double theta = Math.acos(dot) * percent;
        Vector RelativeVec = e.clone().subtract(s.clone().multiply(dot));
        RelativeVec = RelativeVec.normalize();

        return (s.clone().multiply(Math.cos(theta))).add((RelativeVec.clone().multiply(Math.sin(theta))));
    }


    public double clamp(double d, double a, double b) {
        if (d < a)
            d = a;
        if (d > b)
            d = b;
        return d;
    }

    public ArmorStand createArmorStand(Location loca) {
        ArmorStand a = (ArmorStand) loca.getWorld().spawnEntity(loca.clone(), EntityType.ARMOR_STAND);

        a.setInvulnerable(true);
        a.setVisible(false);
        a.setGravity(false);


        return a;
    }

    public void setCanBeSilenced(boolean b) {
        canBeSilenced = b;
    }

    public void setArmorstandHeadPos(ArmorStand a, Vector dir, float offsetPitch, float offsetYaw) {

        Location del = a.getLocation().setDirection(dir);
        //Bukkit.broadcastMessage(""+del.getPitch());
        a.setHeadPose(new EulerAngle(Math.toRadians(del.getPitch() + offsetPitch), Math.PI / 2 + Math.toRadians(del.getYaw() + offsetYaw), 0));
    }


    public void armorStandPitch(Vector dir, ArmorStand a) {
        a.setRightArmPose(EulerAngle.ZERO);
        double top = Math.acos(dir.dot(new Vector(0, 1, 0)));


        EulerAngle ea = new EulerAngle(top, 0, 0);

        a.setHeadPose(ea);
    }

    public void armorStandPitchYaw(Vector dir, ArmorStand a) {
        a.setRightArmPose(EulerAngle.ZERO);
        double yaw = Math.atan(dir.getX() / -dir.getY());
        double pitch = Math.atan(Math.sqrt(dir.getX() * dir.getX() + dir.getY() * dir.getY()) / dir.getZ());

        EulerAngle ea = new EulerAngle(pitch, yaw, 0);

        a.setHeadPose(ea);
    }

    public int getCooldown() {
        return cooldown;
    }

    public void addNoTarget(Entity ent) {
        noTargetEntitys.add(ent);
    }

    public void applySpellKey(Player p) {

        ItemStack is = p.getInventory().getItemInMainHand();
        if (!NBTUtils.getNBT("SpellKey", is).equals("")) {

            spellkey = Integer.parseInt(NBTUtils.getNBT("SpellKey", is));
            //Bukkit.broadcastMessage("HAS KEY" + spellkey);

        }
		/*
		else {
			spellkey = SpellKeyUtils.getNextSpellKey();
			is = NBTUtils.setNBT("SpellKey",""+spellkey, is);
			Bukkit.broadcastMessage("NEW KEY");
		}
		
		final ItemStack a = is;
		p.getInventory().setItemInMainHand(a);
		*/

    }

    public void updateLore() {
        setLore(SaveUtils.getAnalytics().getSpellLore(name));
        setBetterLore(SaveUtils.getAnalytics().getSpellRefinedLore(name));
    }

    public void sendKey(int k) {
        spellkey = k;
    }

    public boolean isSilenced() {
        return silenced.get(caster).filter(spellTypes);
    }

    public void reduceCooldown(int amount) {
        //Bukkit.broadcastMessage("Looking for "+spellkey);
        SpellKeyUtils.reduceCooldown(caster, spellkey, amount);
    }

    public boolean spellHitPlayerEvent(Player p) {
        boolean re = false;
        for (Spell s : hitSpellEvent) {
            if (s.caster == p) {
                re = s.callEvent("spellHitPlayerEvent");
            }

        }
        return re;
    }


    public double getLerp() {
        double a = step;
        double b = steprange;
        return a / b;
    }

    public static int[] getMostCommonColour(Map map) {
        LinkedList list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] rgb = getRGBArr((Integer) me.getKey());
        return rgb;
    }

    public static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};

    }

    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance)
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        return true;
    }


    public boolean callEvent(String name) {
        return false;
    }


    public static void setLeatherArmor(LivingEntity s, Color c) {
        ItemStack bchest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta2 = (LeatherArmorMeta) bchest.getItemMeta();
        meta2.setColor(c);
        bchest.setItemMeta(meta2);
        ItemStack blegs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta3 = (LeatherArmorMeta) blegs.getItemMeta();
        meta3.setColor(c);
        blegs.setItemMeta(meta3);
        ItemStack bboots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta4 = (LeatherArmorMeta) bboots.getItemMeta();
        meta4.setColor(c);
        bboots.setItemMeta(meta4);
        s.getEquipment().setChestplate(bchest);
        s.getEquipment().setLeggings(blegs);
        s.getEquipment().setBoots(bboots);
    }
    // OVERRIDEABLES

    public Spell() {
    }

    public abstract void setUp();

    public abstract void cast();

    public abstract void launch();

    public abstract void move();

    public abstract void display();

    public abstract void onPlayerHit(Player p);

    public abstract void onEntityHit(LivingEntity ent);

    public abstract void onSpellHit(Spell spell);

    public abstract void onBlockHit(Block block);

    public abstract void onDeath();
}
