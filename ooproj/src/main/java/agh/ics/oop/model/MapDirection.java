package agh.ics.oop.model;

import static java.lang.Math.abs;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST,
    ;

    @Override
    public String toString() {
        return switch(this) {
            case EAST -> "E";
            case NORTHWEST -> "NW";
            case WEST -> "W";
            case NORTH -> "N";
            case SOUTHWEST -> "SW";
            case SOUTH -> "S";
            case SOUTHEAST -> "SE";
            case NORTHEAST -> "NE";
        };
    }

    public static MapDirection next(MapDirection d) {
        return switch(d) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public static MapDirection previous(MapDirection d) {
        return switch(d) {
            case NORTH -> NORTHWEST;
            case NORTHWEST -> WEST;
            case WEST -> SOUTHWEST;
            case SOUTHWEST -> SOUTH;
            case SOUTH -> SOUTHEAST;
            case SOUTHEAST -> EAST;
            case EAST -> NORTHEAST;
            case NORTHEAST -> NORTH;
        };
    }

//    public static MapDirection turn(MapDirection currDir, int timesTurned) {
//        if (timesTurned >= 0) {
//            timesTurned %= 8;
//            for (int i = 0; i < timesTurned; i++)
//                currDir = next(currDir);
//        }
//        else {
//            timesTurned %= 8;
//            for (int i = 0; i < abs(timesTurned); i++)
//                currDir = previous(currDir);
//        }
//        return currDir;
//    }

    public static Vector2d toUnitVector(MapDirection d) {
        return switch(d) {
            case NORTH -> new Vector2d(0, 1);
            case WEST -> new Vector2d(-1, 0);
            case SOUTH -> new Vector2d(0, -1);
            case EAST -> new Vector2d(1, 0);
            case NORTHEAST -> new Vector2d(1, 1);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }


}
