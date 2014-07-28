/**
 * 
 */
package br.odb.libscene.old;

import br.odb.utils.math.Vec3;

/**
 * @author monty
 * 
 */
public class ActorCommand {
	public Vec3 translate;
	public Vec3 rotate;
	public Vec3 scale;
	public ActorConstants code;

	public ActorCommand() {
		translate = new Vec3();
		rotate = new Vec3();
		scale = new Vec3();

		code = ActorConstants.NOP;

		scale.set(1.0f, 1.0f, 1.0f);
	}

	public static ActorCommand makeCommandFromCode(ActorConstants cmdCode) {
		ActorCommand toReturn = new ActorCommand();

		toReturn.code = cmdCode;

		switch (cmdCode) {
		case MOVE_N:
			toReturn.translate.z = (0.05f);
			break;
		case MOVE_S:
			toReturn.translate.z = (-0.05f);
			break;
		case MOVE_W:
			toReturn.translate.x = (-0.05f);
			break;
		case MOVE_E:
			toReturn.translate.x = (0.05f);
			break;
		case MOVE_DOWN:
			toReturn.translate.y = (-0.05f);
			break;
		case MOVE_UP:
			toReturn.translate.y = (0.05f);
			break;
		case TURN_L:
			toReturn.rotate.z = (0.45f);
			break;
		case TURN_R:
			toReturn.rotate.z = (-0.45f);
			break;
		case FIRE:
			break;
		case NOP:
			break;
		case UNDO:
			break;
		default:
			break;

		}

		return toReturn;
	}

}
