package frc.robot.subsytems;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Comands extends SubsystemBase {
    public Comands(){

    }

    private Intake intake = RobotContainer.m_intake;
    private IntakeWrist intakeWrist = RobotContainer.m_intakeWrist;
    private Shooter shooter = RobotContainer.m_shooter;
    private Feeder feeder = RobotContainer.m_feeder;
    private ShooterWrist shooterWrist = RobotContainer.m_shooterWrist;
    private ClimbRight climbRight = RobotContainer.m_climbRight;
    private ClimbLeft climbLeft = RobotContainer.m_climbLeft;
    public Command intakeSequence(){
    return this.runOnce(() -> intakeWrist.floor().deadlineWith(intake.intakeBreakCommand())
    .andThen(intakeWrist.storeIntake()).withTimeout(.5)
    .andThen(intake.intakeCommand())
    .alongWith(shooter.shootIntakeSpeedCommand())
    .alongWith(feeder.feedInCommand()).withTimeout(.5)
    .andThen(feeder.stopFeederCommand())
    .alongWith(shooter.stopShooterCommand())
    .alongWith(intake.stopIntakeCommand()));
    }
    /*Command intakeSource = shooterWrist.source().deadlineWith(shooter.shooterIntakeCommand())
    .andThen(shooterWrist.storeShooter())
    .alongWith(shooter.stopFeederCommand())
    .alongWith(shooter.stopShooterCommand());*/
    
    public Command shootAmpCommand(){
    return this.runOnce(() -> shooter.amp()
    .alongWith(shooterWrist.shootAmp()).withTimeout(.7)
    .andThen(feeder.feedOutCommand()).withTimeout(.5)
    .andThen(feeder.stopFeederCommand())
    .alongWith(shooter.stopShooterCommand())
    .alongWith(shooterWrist.storeShooter()));
    }
    public Command shootCloseCommand(){
    return this.runOnce(() -> shooter.shootCloseSpeedCommand()
    .alongWith(shooterWrist.shootClose()).withTimeout(.8)
    .andThen(feeder.feedOutCommand()).withTimeout(.5)
    .andThen(feeder.stopFeederCommand())
    .alongWith(shooter.stopShooterCommand())
    .alongWith(shooterWrist.storeShooter()));
    }
    public Command shootFarCommand(){
    return this.runOnce(() -> shooter.shootFarSpeedCommand()
    .alongWith(shooterWrist.shootFar()).withTimeout(.8)
    .andThen(feeder.feedOutCommand()).withTimeout(.5)
    .andThen(feeder.stopFeederCommand())
    .alongWith(shooter.stopShooterCommand())
    .alongWith(shooterWrist.storeShooter()));
    }


    
    public Command raiseLeftClimberCommand(){
    return this.runOnce(() -> climbLeft.leftRaiseCommand().withTimeout(1)
    .andThen(climbLeft.commandRaiseLeft())
    .andThen(climbLeft.stopClimbLeftCommand()));
    }
    public Command raiseRightClimberCommand(){
    return this.runOnce(() -> climbRight.rightRaiseCommand().withTimeout(1)
    .andThen(climbRight.commandRaiseRight())
    .andThen(climbRight.stopClimbRightCommand()));
    }
    public Command climbLeftClimberCommand(){
    return this.runOnce(() -> climbLeft.leftClimbCommand().withTimeout(1)
    .andThen(climbLeft.commandClimbLeft())
    .andThen(climbLeft.stopClimbLeftCommand()));
    }
    public Command climbRightClimberCommand(){
        return this.runOnce(() -> climbRight.rightClimbCommand().withTimeout(1)
    .andThen(climbRight.commandClimbRight())
    .andThen(climbRight.stopClimbRightCommand()));
    }
}
