package spells.spells;

import esze.objects.Vector2D;
import esze.utils.ParUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import spells.spellcore.Spell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class RuneDraw extends Spell {

    ArrayList<Vector> offsets = new ArrayList<Vector>();
    Vector normalVec;
    Vector rightVec;
    Vector topVec;
    Vector vec;
    double vlen = 5;
    double yang;

    public RuneDraw() {
        steprange = 25;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void cast() {

    }

    @Override
    public void launch() {
        vec = caster.getLocation().getDirection().multiply(vlen);
        normalVec = caster.getLocation().getDirection();
        rightVec = normalVec.clone().crossProduct(new Vector(0, 1, 0)).normalize();
        topVec = rightVec.clone().crossProduct(normalVec.clone()).normalize();
        yang = singedangle(caster.getLocation().getDirection(), new Vector(0, 1, 0));
    }

    @Override
    public void move() {
        Vector rotVec = getRotVec();
        Vector v = caster.getEyeLocation().getDirection().multiply(vlen);
        v = v.rotateAroundAxis(rotVec, yang);
        v = v.multiply(vlen / v.getY());
        v = v.rotateAroundAxis(rotVec, -yang);

        offsets.add(v);
        if (offsets.size() > 50) {
            offsets.remove(0);
        }
        for (Vector vec : offsets) {
            ParUtils.createParticle(Particle.BUBBLE, caster.getEyeLocation().add(vec), 0, 0, 0, 5, 0);
        }
    }

    public Vector getRotVec() {
        double x1 = -normalVec.getZ() * 1 / normalVec.getX();
        Vector rotVec = new Vector(x1, 0, 1);
        return rotVec;
    }

    public double singedangle(Vector v, Vector v2) {
        return Math.atan2(v2.getY(), v2.getX()) - Math.atan2(v.getY(), v.getX());
    }

    @Override
    public void display() {

    }

    @Override
    public void onPlayerHit(Player p) {

    }

    @Override
    public void onEntityHit(LivingEntity ent) {

    }

    @Override
    public void onSpellHit(Spell spell) {

    }

    @Override
    public void onBlockHit(Block block) {

    }

    @Override
    public void onDeath() {
        ArrayList<Vector2D> vectors = new ArrayList<Vector2D>();

        for (Vector vec : offsets) {
            double y = vec.clone().dot(topVec.clone());
            double x = vec.clone().dot(rightVec.clone());
            vectors.add(new Vector2D(x, y));
        }

        ArrayList<Vector2D> drawVector = new ArrayList<Vector2D>();
        int index = 0;
        for (Vector2D v : vectors) {
            index++;

            if (index < vectors.size()) {
                drawVector.add(vectors.get(index).subtract(v));
            }
        }


        writeToFile("Line", drawVector);
        playSound(Sound.BLOCK_NOTE_BLOCK_PLING, loc, 1D, 1D);
    }


    public void writeToFile(String shape, ArrayList<Vector2D> vectors) {
        try {
            File file1 = new File("vectordata.txt");
            if (!file1.exists()) {
                FileOutputStream fileOut = new FileOutputStream("vectordata.txt");

                fileOut.write("0".getBytes());

                fileOut.close();
            }


            BufferedReader file = new BufferedReader(new FileReader("vectordata.txt"));
            ArrayList<String> lines = new ArrayList<String>();
            String line;

            while ((line = file.readLine()) != null) {
                lines.add(line);
            }
            file.close();
            //------------ ShapeCount
            int count = Integer.parseInt(lines.get(0)) + 1;
            lines.set(0, "" + count);
            //
            lines.add(shape);
            lines.add("" + vectors.size());
            for (Vector2D vec : vectors) {
                lines.add(vec.getX() + " " + vec.getY());
            }

            FileOutputStream fileOut = new FileOutputStream("vectordata.txt");
            for (String line2 : lines) {
                fileOut.write((line2 + "\n").getBytes());
            }
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
