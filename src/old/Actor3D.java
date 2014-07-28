package br.odb.libscene.old;

import br.odb.libscene.SceneObject3D;
import br.odb.libscene.SpaceRegion;
import br.odb.utils.Utils;
import br.odb.utils.math.Vec2;
import br.odb.utils.math.Vec3;

/**
 * 
 * @author Daniel "Monty" Monteiro
 */
public class Actor3D extends SceneObject3D {

	public int kind;
	private Vec3 previousPosition;
	private Vec3 acceleration;
	public SpaceRegion currentRegion;
	private float angle;
	private float speed = 1.5f;
	public int id;
	public boolean alive = true;

	public void haltAcceleration() {
		acceleration.set(0, 0, 0);
	}

	public void setAlive(boolean b) {
		alive = b;
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public String toString() {
		return "a " + currentRegion + " "
				+ emissiveLightningIntensity + " " + kind;
	}

	public Actor3D() {
		super();
		acceleration = new Vec3();
		previousPosition = new Vec3();
	}

	public Actor3D(Actor3D actor) {
		super(actor);

		kind = actor.kind;
		previousPosition = new Vec3();
		acceleration = new Vec3();
		currentRegion = actor.currentRegion;
		angle = actor.angle;
		id = actor.id;
	}

	public void tick() {
		origin.addTo(acceleration);
		acceleration.scale(0.8f);
	}

	public Vec3 getDirectionVector() {

		float xz = angle * 3.1415925f / (180.0f);
		float cos = -(float) Math.cos(xz);
		float sin = (float) Math.sin(xz);
		return new Vec3(sin, 0, cos);
	}

	public void consume(ActorConstants cmdCode) {

		switch (cmdCode) {
		case MOVE_N: {
			Vec2 translation = Utils
					.getTranslationForOctant((int) (angle / Utils.transformTableIncrements));
			accel(speed * translation.x, 0.0f, -translation.y * speed);
		}
			break;
		case MOVE_S: {
			Vec2 translation = Utils
					.getTranslationForOctant((int) (angle / Utils.transformTableIncrements) + 6);
			accel(speed * translation.x, 0.0f, -translation.y * speed);
		}
			break;
		case MOVE_W: {
			Vec2 translation = Utils
					.getTranslationForOctant((int) (angle / Utils.transformTableIncrements) + 9);
			accel(speed * translation.x, 0.0f, translation.y * speed);
		}
			break;
		case MOVE_E: {
			Vec2 translation = Utils
					.getTranslationForOctant((int) (angle / Utils.transformTableIncrements) + 3);
			accel(speed * translation.x, 0.0f, translation.y * speed);
		}
			break;
		case MOVE_DOWN:
			move( new Vec3( 0, -1, 0 ) );
			break;
		case MOVE_UP:
			move( new Vec3( 0, 1, 0) );
			break;
		case TURN_L:
			angle -= Utils.transformTableIncrements;
			acceleration.scale(0.0f);
			break;
		case TURN_R:

			angle += Utils.transformTableIncrements;
			acceleration.scale(0.0f);
			break;
		case NOP:
			break;
		case UNDO:
			break;
		default:
			break;

		}
	}

	public float getAngleXZ() {
		return angle;
	}

	public void setAngleXZ(float angle) {
		this.angle = angle;
	}

	public void rotateXZ(float angle) {
		this.angle += angle;
	}

	public void accel(float x, float y, float z) {
		acceleration.addTo(x, y, z);
	}

	public void checkpointPosition() {
		previousPosition.copy( origin );
	}

	public void undo() {
		origin.set(previousPosition);
	}
}
