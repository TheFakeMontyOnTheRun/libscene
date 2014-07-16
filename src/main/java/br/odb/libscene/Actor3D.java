package br.odb.libscene;

import br.odb.utils.Utils;
import br.odb.utils.math.Vec2;
import br.odb.utils.math.Vec3;

/**
 * 
 * @author Daniel "Monty" Monteiro
 */
public class Actor3D extends SceneObject3D {

	private int kind;
	private Vec3 previousPosition;
	private Vec3 acceleration;
	public int currentSector;
	public int candelas;
	private float angle;
	public float speed = 1.5f;
	private int id;
	private Actor3D generated;
	private Actor3D owner;
	private boolean alive = true;

	public Actor3D getOwner() {
		return owner;
	}

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
		return "a " + getCurrentSector() + " "
				+ getEmissiveLightningIntensity() + " " + kind;
	}

	public int getCurrentSector() {
		return currentSector;
	}

	public Actor3D() {
		super();
		acceleration = new Vec3();
		previousPosition = new Vec3();
		generated = null;
	}

	public Actor3D(Actor3D actor) {
		super(actor);

		owner = actor;
		generated = null;
		kind = actor.kind;
		previousPosition = new Vec3();
		acceleration = new Vec3();
		currentSector = actor.currentSector;
		candelas = actor.candelas;
		angle = actor.angle;
		id = actor.id;
	}

	public void tick() {
		getPosition().addTo(acceleration);
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
			move(0, -1, 0);
			break;
		case MOVE_UP:
			move(0, 1, 0);
			break;
		case TURN_L:
			angle -= Utils.transformTableIncrements;
			acceleration.scale(0.0f);
			break;
		case TURN_R:

			angle += Utils.transformTableIncrements;
			acceleration.scale(0.0f);
			break;
		case FIRE:
			fire();
			break;
		case NOP:
			break;
		case UNDO:
			break;
		default:
			break;

		}
	}

	public void setGenerated(Actor3D generated) {
		this.generated = generated;
	}

	public void fire() {
		setGenerated(generateCopy());
	}

	public Actor3D generateCopy() {
		return new Actor3D(this);
	}

	public void scale(Vec3 scale) {

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

	public void move(float x, float y, float z) {
		Vec3 p = getPosition();

		p.addTo(x, y, z);
	}

	public void accel(float x, float y, float z) {
		acceleration.addTo(x, y, z);
	}

	public void checkpointPosition() {
		previousPosition.copy(getPosition());
	}

	@Override
	public void move(Vec3 translate) {

		setPosition(getPosition().add(translate));
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void undo() {
		getPosition().copy(previousPosition);
	}

	public void setCurrentSector(int sector) {
		currentSector = sector;

	}

	@Override
	public void setEmissiveLightningIntensity(int light) {
		candelas = light;

	}

	public Actor3D checkGenerated() {
		Actor3D toBeReturned = generated;
		generated = null;
		return toBeReturned;
	}

	public void destroy() {

		previousPosition = null;
		generated = null;
		owner = null;
	}
}
