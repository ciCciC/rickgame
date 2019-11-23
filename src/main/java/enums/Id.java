package enums;

public enum Id {
    player,
    alienEnemy,
    zombieEnemy,
    box,
    wall,
    wallLeft,
    wallRight,
    magicWall,
    groundGrass,
    sideWallLeft,
    sideWallRight,
    squareWall,
    bullet,
    poop,
    explosion,
    cube,
    christmastree,
    topbar,
    textbox;

    public static boolean isEnemy(Id idToCompare) { return idToCompare == Id.alienEnemy || idToCompare == Id.zombieEnemy; }

    public static boolean isWall(Id idToCompare) { return idToCompare == Id.wall ||
            idToCompare == Id.wallLeft || idToCompare == Id.wallRight ||
            idToCompare == Id.squareWall || idToCompare == Id.box ||
            idToCompare == Id.magicWall; }
}
