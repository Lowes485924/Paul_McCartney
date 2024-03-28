package frc.robot.subsytems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants;
//import frc.robot.Constants.shooterConstants.shooterPID;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;

public class Feeder extends SubsystemBase {
    CANSparkMax m_Feeder = new CANSparkMax(Constants.shooterConstants.feederMotorCanID, MotorType.kBrushless);
    public Feeder(){
        m_Feeder.restoreFactoryDefaults();
        m_Feeder.setIdleMode(IdleMode.kBrake);
        m_Feeder.setSmartCurrentLimit(Constants.shooterConstants.feederMotorCurrentLimit);
        m_Feeder.burnFlash();
    }
    public Command feedInCommand(){
        return this.run(() -> m_Feeder.set(Constants.shooterConstants.feedInSpeed));
    }
    public static void feedIn(){
        RobotContainer.m_feeder.feedInCommand();
    }
    public Command feedOutCommand(){
        return this.startEnd(() -> m_Feeder.set(Constants.shooterConstants.feedEjectSpeed), () -> m_Feeder.stopMotor());
    }

    public Command stopFeederCommand(){
        return this.runOnce(() -> m_Feeder.stopMotor());
    }
}
