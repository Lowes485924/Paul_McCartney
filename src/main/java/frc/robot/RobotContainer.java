// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsytems.ClimbRight;
import frc.robot.commands.feedInCommand;
import frc.robot.subsytems.ClimbLeft;
import frc.robot.subsytems.Shooter;
import frc.robot.subsytems.Feeder;
import frc.robot.subsytems.ShooterWrist;
import frc.robot.subsytems.IntakeWrist;
import frc.robot.subsytems.Intake;
import frc.robot.subsytems.Comands;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.cameraserver.CameraServer;

public class RobotContainer {
  double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController driver = new CommandXboxController(0); // My joystick
  //private final CommandXboxController gunner = new CommandXboxController(1);
  private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain
  public final static Intake m_intake = new Intake();
  public final static IntakeWrist m_intakeWrist = new IntakeWrist();
  public final static ShooterWrist m_shooterWrist = new ShooterWrist();
  public final static Shooter m_shooter = new Shooter();
  public final static Feeder m_feeder = new Feeder();
  public final static ClimbLeft m_climbLeft = new ClimbLeft();
  public final static ClimbRight m_climbRight = new ClimbRight();
  public final static Comands m_commands = new Comands();
  private final Telemetry logger = new Telemetry(MaxSpeed);
  private final SendableChooser<Command> autoChooser;
  //private Command runAuto = drivetrain.getAutoPath(autoChooser);

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                        // driving in open loop
  //private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  //private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  //private final Telemetry logger = new Telemetry(MaxSpeed);
  
  
  

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-driver.getLeftY() * MaxSpeed) // Drive forward with
                                                                                           // negative Y (forward)
            .withVelocityY(-driver.getLeftX() * MaxSpeed) // Drive left with negative X (left)
            .withRotationalRate(-driver.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
        ));

    /*driver.povUp().whileTrue(drivetrain.applyRequest(() -> brake));
    driver.povDown().whileTrue(drivetrain
        .applyRequest(() -> point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))));*/

    // reset the field-centric heading on left bumper press
    driver.povLeft().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));
    //driver.povRight().onTrue(m_shooter.stopShooterCommand());
    driver.rightTrigger().onTrue(m_shooter.shootFarSpeedCommand());
    driver.rightBumper().onTrue(m_shooter.shootCloseSpeedCommand());
    driver.leftTrigger().onTrue(m_shooter.intake());
    //driver.rightBumper().onTrue(m_shooter.shootCloseSpeedCommand());
    driver.leftBumper().onTrue(m_shooter.amp());
    driver.a().onTrue(m_intake.intakeCommand());
    //driver.button(7).whileTrue(m_shooter.feedInCommand());
    driver.x().whileTrue(m_feeder.feedOutCommand());
    driver.x().whileFalse(m_feeder.feedInCommand());

    driver.a().onTrue(m_intakeWrist.floor());
    //driver.povRight().onTrue(m_intakeWrist.amp());
    driver.y().onTrue(m_intakeWrist.storeIntake());
    driver.leftTrigger().onTrue(m_shooterWrist.storeShooter());
    driver.rightTrigger().onTrue(m_shooterWrist.shootFar());
    driver.rightBumper().onTrue(m_shooterWrist.shootClose());
    driver.leftBumper().onTrue(m_shooterWrist.shootAmp());
  


    //driver.a().onTrue(m_commands.intakeSequence());
    //driver.b().onTrue(m_commands.resetCommand());
    //driver.leftBumper().onTrue(m_commands.shootAmpCommand());
    //driver.leftTrigger().onTrue(m_commands.sourceIntakeCommand());
    //driver.rightBumper().onTrue(m_commands.shootCloseCommand());
    //driver.rightTrigger().onTrue(m_commands.shootFarCommand());
    //gunner.leftBumper().onTrue(m_commands.raiseLeftClimberCommand());
    //gunner.leftTrigger().onTrue(m_commands.climbLeftClimberCommand());
    //gunner.rightBumper().onTrue(m_commands.raiseRightClimberCommand());
    //gunner.rightTrigger().onTrue(m_commands.climbRightClimberCommand());

    //joystick.y().onTrue(m_robotArm.speaker());
    //joystick.x().onTrue(m_robotArm.amp());
    //joystick.a().onTrue(m_robotArm.source());
    //joystick.b().onTrue(m_robotArm.store());

    driver.povUp().whileTrue(m_climbLeft.leftRaiseCommand());
    driver.povUp().whileTrue(m_climbRight.rightRaiseCommand());
    driver.povDown().whileTrue(m_climbLeft.leftClimbCommand());
    driver.povDown().whileTrue(m_climbRight.rightClimbCommand());
  }

  public RobotContainer(){
    configureBindings();
    autoChooser = AutoBuilder.buildAutoChooser();
    NamedCommands.registerCommand("First Shot", m_commands.shootCloseCommand());
    NamedCommands.registerCommand("Second Shot", m_commands.shootCloseCommand());
    NamedCommands.registerCommand("Third Shot", m_commands.shootCloseCommand());
    NamedCommands.registerCommand("Intake", new ParallelCommandGroup(
      new feedInCommand(m_feeder, -.5)).withTimeout(1)
    );
    SmartDashboard.putData("Auto Chooser", autoChooser);
    CameraServer.startAutomaticCapture();
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
