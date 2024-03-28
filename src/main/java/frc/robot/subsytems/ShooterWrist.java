package frc.robot.subsytems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
//import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkPIDController;
import com.revrobotics.AbsoluteEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class ShooterWrist extends SubsystemBase {
    
    private final CANSparkMax m_shooterWrist = new CANSparkMax(Constants.shooterWristConstants.shooterWristMotorCanID,MotorType.kBrushless);
    
    private AbsoluteEncoder m_shooterWristEncoder;
    
    public static SparkPIDController m_shooterWristPID;

    public ShooterWrist(){
        //Config controllers, encoder, and pid
        
       m_shooterWrist.restoreFactoryDefaults();
        m_shooterWristEncoder = m_shooterWrist.getAbsoluteEncoder(Type.kDutyCycle);
        m_shooterWrist.setInverted(Constants.shooterWristConstants.shooterWristInverted);
        m_shooterWristEncoder.setInverted(Constants.shooterWristConstants.wristEncoderInverted);
        m_shooterWristEncoder.setPositionConversionFactor(360);
       m_shooterWristPID = m_shooterWrist.getPIDController();
        m_shooterWristPID.setFeedbackDevice(m_shooterWristEncoder);
        m_shooterWristPID.setP(Constants.shooterWristConstants.wristPID.kP);
        m_shooterWristPID.setI(Constants.shooterWristConstants.wristPID.kI);
        m_shooterWristPID.setD(Constants.shooterWristConstants.wristPID.kD);
        
        m_shooterWrist.setIdleMode(IdleMode.kBrake);
       m_shooterWrist.setSmartCurrentLimit(Constants.shooterWristConstants.shooterWristMotorSmartCurrentLimit);
       m_shooterWrist.burnFlash();
    }

    public void periodic() {
        SmartDashboard.putNumber("Shooter Wrist Angle", m_shooterWristEncoder.getPosition());
        //SmartDashboard.putNumber("Arm Angle",  m_armEncoder.getPosition());
    }

    public Command intakeWristUpCommand(){
        return this.startEnd(() -> m_shooterWrist.set(Constants.shooterWristConstants.shooterWristUpSpeed), () -> m_shooterWrist.stopMotor());
    }
    public Command intakeWristDownCommand(){
        return this.startEnd(() -> m_shooterWrist.set(Constants.shooterWristConstants.shooterWristDownSpeed), () -> m_shooterWrist.stopMotor());
    }
    
    public Command stopIntakeWristCommand(){
        return this.runOnce(() -> m_shooterWrist.stopMotor());
    }

    public void setIntakeWristPosition(double wristAngle) {
        m_shooterWristPID.setReference(wristAngle, CANSparkMax.ControlType.kPosition);
    }
    public Command setIntakeWristPositionCommand(double wristAngle) {
        return this.runOnce(() -> setIntakeWristPosition(wristAngle));
    }

    
    public Command shootClose() {
        return this.runOnce(() -> setIntakeWristPosition(40));
    }

    public Command shootAmp() {
        return this.runOnce(() -> setIntakeWristPosition(5));
    }

    public Command shootFar() {
        return this.runOnce(() -> setIntakeWristPosition(63));
    }

    public Command storeShooter() {
        return this.runOnce(() -> setIntakeWristPosition(90));
    }

    public Command source() {
        return this.runOnce(() -> setIntakeWristPosition(16));
    }
}
