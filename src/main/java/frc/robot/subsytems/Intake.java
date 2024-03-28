package frc.robot.subsytems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;

public class Intake extends SubsystemBase {
    CANSparkMax m_Intake = new CANSparkMax(Constants.intakeConstants.intakeMotorCanID, MotorType.kBrushless);
    DigitalInput intakeBeamBreak = new DigitalInput(Constants.intakeConstants.intakeBeamBreakPort);

    public Intake(){
        m_Intake.setIdleMode(IdleMode.kBrake);
        m_Intake.setInverted(Constants.intakeConstants.intakeInverted);
        m_Intake.setSmartCurrentLimit(Constants.intakeConstants.intakeMotorCurrentLimit);
        m_Intake.burnFlash();
    }
    private void intakeBreak(){
        while(intakeBeamBreak.get()){
            intakeCommand();
        }
        stopIntakeCommand();
    }
    public Command intakeBreakCommand(){
        return this.runOnce(() ->  intakeBreak());
    }
    public Command intakeCommand(){
        return this.startEnd(() -> m_Intake.set(-Constants.intakeConstants.intakeSpeed), () -> m_Intake.stopMotor());
    }
    public Command intakeEjectCommand(){
        return this.startEnd(() -> m_Intake.set(Constants.intakeConstants.intakeEjectSpeed), () -> m_Intake.stopMotor());
    }

    public Command stopIntakeCommand(){
        return this.runOnce(() -> m_Intake.stopMotor());
    }
}
