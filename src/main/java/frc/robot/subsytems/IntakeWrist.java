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

public class IntakeWrist extends SubsystemBase {
    
    private final CANSparkMax m_intakeWrist = new CANSparkMax(Constants.intakeWristConstants.intakeWristMotorCanID,MotorType.kBrushless);
    
    private AbsoluteEncoder m_intakeWristEncoder;
    
    public static SparkPIDController m_intakeWristPID;

    public IntakeWrist(){
        //Config controllers, encoder, and pid
        
        m_intakeWrist.restoreFactoryDefaults();
        m_intakeWristEncoder = m_intakeWrist.getAbsoluteEncoder(Type.kDutyCycle);
        m_intakeWristEncoder.setInverted(Constants.intakeWristConstants.wristEncoderInverted);
        m_intakeWrist.setInverted(Constants.intakeWristConstants.intakeWristInverted);
        m_intakeWristEncoder.setPositionConversionFactor(360);
        m_intakeWristPID = m_intakeWrist.getPIDController();
        m_intakeWristPID.setFeedbackDevice(m_intakeWristEncoder);
        m_intakeWristPID.setP(Constants.intakeWristConstants.wristPID.kP);
        m_intakeWristPID.setI(Constants.intakeWristConstants.wristPID.kI);
        m_intakeWristPID.setD(Constants.intakeWristConstants.wristPID.kD);
        
        m_intakeWrist.setIdleMode(IdleMode.kBrake);
        m_intakeWrist.setSmartCurrentLimit(Constants.intakeWristConstants.intakeWristMotorSmartCurrentLimit);
        m_intakeWrist.burnFlash();
    }

    public void periodic() {
        SmartDashboard.putNumber("Intake Wrist Angle", m_intakeWristEncoder.getPosition());
        //SmartDashboard.putNumber("Arm Angle",  m_armEncoder.getPosition());
    }

    public Command intakeWristUpCommand(){
        return this.startEnd(() -> m_intakeWrist.set(Constants.intakeWristConstants.intakeWristUpSpeed), () -> m_intakeWrist.stopMotor());
    }
    public Command intakeWristDownCommand(){
        return this.startEnd(() -> m_intakeWrist.set(Constants.intakeWristConstants.intakeWristDownSpeed), () -> m_intakeWrist.stopMotor());
    }
    
    public Command stopIntakeWristCommand(){
        return this.runOnce(() -> m_intakeWrist.stopMotor());
    }

    public void setIntakeWristPosition(double wristAngle) {
        
        m_intakeWristPID.setReference(wristAngle, CANSparkMax.ControlType.kPosition);
    }
    public Command setIntakeWristPositionCommand(double wristAngle) {
        return this.runOnce(() -> setIntakeWristPosition(wristAngle));
    }

    
    public Command shoot() {
        return this.runOnce(() -> setIntakeWristPosition(90));
    }

    public Command amp() {
        return this.runOnce(() -> setIntakeWristPosition(100));
    }

    public Command floor() {
        return this.runOnce(() -> setIntakeWristPosition(20));
    }

    public Command storeIntake() {
        return this.runOnce(() -> setIntakeWristPosition(115));
    }
}
