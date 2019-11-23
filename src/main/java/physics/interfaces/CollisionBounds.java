package physics.interfaces;

import java.awt.*;

public interface CollisionBounds {
    Rectangle getBoundsRight();
    Rectangle getBoundsLeft();
    Rectangle getBoundsBottom();
    Rectangle getBoundsTop();
}
