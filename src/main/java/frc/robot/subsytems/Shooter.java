package frc.robot.subsytems;

//import edu.wpi.first.units.Velocity;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.units.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;
//import frc.robot.Constants.shooterConstants.shooterPID;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

public class Shooter extends SubsystemBase {
    CANSparkFlex m_Shooter = new CANSparkFlex(Constants.shooterConstants.shooterMotorCanID, MotorType.kBrushless);
    
    DigitalInput shooterBeamBreak = new DigitalInput(Constants.shooterConstants.shooterBeamBreakPort);

    public static SparkPIDController m_shooterPID;

    public Shooter(){
        m_Shooter.restoreFactoryDefaults();
        m_shooterPID = m_Shooter.getPIDController();
        m_shooterPID.setFeedbackDevice(m_Shooter.getEncoder());
        m_shooterPID.setP(Constants.shooterConstants.shooterPID.kP);
        m_shooterPID.setI(Constants.shooterConstants.shooterPID.kI);
        m_shooterPID.setD(Constants.shooterConstants.shooterPID.kD);
        //m_shooterPID.setReference(m_Shooter.get(), CANSparkFlex.controlType.kVelocity)
        m_Shooter.setIdleMode(IdleMode.kCoast);
        m_Shooter.setSmartCurrentLimit(Constants.shooterConstants.shooterCurrentLimit);
        m_Shooter.burnFlash();
    }

    /*public Command shootCloseSpeedCommand(){
        return this.run(() -> m_Shooter.set(Constants.shooterConstants.shootCloseSpeed));
    }
    public Command shootFarCommand() {
        return this.startEnd(() -> m_Shooter.set(Constants.shooterConstants.shootFarSpeed),
                () -> m_shooterPID.setReference(, CANSparkMax.ControlType.kVelocity));

    }*/
    private void shooterIntake(){
        while(shooterBeamBreak.get()){
            Feeder.feedIn();
            shooterIntakeCommand();
        }
    }
    public Command shooterIntakeCommand(){
        return runOnce(() -> shooterIntake());
    }
    public void setShooter(double speed) {
        m_shooterPID.setReference(speed, CANSparkMax.ControlType.kVelocity);
    }
    /*public Command shootFarSpeedCommand(){
        return this.run(() -> m_Shooter.set(Constants.shooterConstants.shootFarSpeed));
    }*/
    public Command shootIntakeSpeedCommand(){
        return this.run(() -> m_Shooter.set(Constants.shooterConstants.shootIntakeSpeed));
    }

    public Command stopShooterCommand(){
        return this.runOnce(() -> m_Shooter.stopMotor());
    }

    public Command shootFarSpeedCommand() {
        return this.runOnce(() -> setShooter(-6000));
    }

    public Command amp() {
        return this.runOnce(() -> setShooter(-1000));
    }

    public Command intake() {
        return this.runOnce(() -> setShooter(1000));
    }

    public Command shootCloseSpeedCommand() {
        return this.runOnce(() -> setShooter(-4000));
    }
}
