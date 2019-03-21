package player;

import java.util.function.Consumer;

public class PlayerIOBuilder {
	
	protected Runnable crouchAction;
	protected Runnable jumpAction;
	protected Runnable pauseAction;
	protected Consumer<Boolean> accelerateAction;
	protected Consumer<Boolean> decelerateAction;
	
	protected PlayerIOBuilder() {
		// just sitting here being cool
	}
	
	protected Runnable getCrouchAction() {
		return crouchAction;
	}
	
	protected Runnable getJumpAction() {
		return jumpAction;
	}
	
	protected Runnable getPauseAction() {
		return pauseAction;
	}
	
	protected Consumer<Boolean> getAccelerateAction() {
		return accelerateAction;
	}
	
	protected Consumer<Boolean>  getDecelerateAction() {
		return decelerateAction;
	}
	
	public static class Builder{
		private PlayerIOBuilder player = new PlayerIOBuilder();
		
		public Builder setCrouchAction(Runnable crouchAction) {
			player.crouchAction = crouchAction;
			return this;
		}
		
		public Builder setJumpAction(Runnable jumpAction) {
			player.jumpAction = jumpAction;
			return this;
		}
		
		public Builder setPauseAction(Runnable pauseAction) {
			player.pauseAction = pauseAction;
			return this;
		}
		
		public Builder setAccelerateAction(Consumer<Boolean> accelerateAction) {
			player.accelerateAction = accelerateAction;
			return this;
		}
		
		public Builder setDecelerateAction(Consumer<Boolean> decelerateAction) {
			player.decelerateAction = decelerateAction;
			return this;
		}
		
		public PlayerIOBuilder build() {
			if(player.crouchAction == null || player.jumpAction == null ||
					player.pauseAction == null || player.accelerateAction == null ||
					player.decelerateAction == null) {
				throw new IllegalStateException("All setters must be called");
			}
			return player;
		}
	}
}

//thingy = new thingy((new PlayerIO.Builder).setCrouchAction(THING)
//						                    .setJumpAction(THING)
//					 	                    .build());
// new PlayerIO