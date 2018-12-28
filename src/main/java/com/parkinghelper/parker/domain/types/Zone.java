package com.parkinghelper.parker.domain.types;

import org.postgresql.geometric.PGbox;
import org.postgresql.geometric.PGpoint;

import java.sql.SQLException;

public class Zone extends PGbox {
    public Zone(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    public Zone(PGpoint p1, PGpoint p2) {
        super(p1, p2);
    }

    public Zone(String s) throws SQLException {
        super(s);
    }

    public Zone() {
    }

    public boolean contains(PGpoint point){
        return
                (Math.min(this.point[0].x, this.point[1].x) < point.x && Math.max(this.point[0].x, this.point[1].x) > point.x) &&
                        (Math.min(this.point[0].y, this.point[1].y) < point.y && Math.max(this.point[0].y, this.point[1].y) > point.y);
    }
}
