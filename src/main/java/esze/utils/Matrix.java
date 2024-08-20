package esze.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Matrix {
    public static Vector rotateMatrixVectorFunktion(Vector v, Location loc) {

        double yaw = loc.getYaw() / 180.0 * Math.PI;
        double pitch = (loc.getPitch() + 90) / 180.0 * Math.PI;
        v = rotateX(v, pitch);
        v = rotateY(v, -yaw);

        return v;
    }


    public static Vector rotateX(Vector v, double a) {
        double y = Math.cos(a) * v.getY() - Math.sin(a) * v.getZ();
        double z = Math.sin(a) * v.getY() + Math.cos(a) * v.getZ();

        v.setY(y).setZ(z);
        return v;
    }

    public static Vector rotateY(Vector v, double c) {
        double x = Math.cos(c) * v.getX() + Math.sin(c) * v.getZ();
        double z = -Math.sin(c) * v.getX() + Math.cos(c) * v.getZ();
        v.setX(x).setZ(z);
        return v;
    }


    public static Location alignLocRotation(Location l, Vector relative) {
        relative = rotateMatrixVectorFunktion(relative, l);
        return l.clone().add(relative);

    }


}
