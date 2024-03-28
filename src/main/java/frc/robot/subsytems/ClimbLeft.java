package frc.robot.subsytems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbLeft extends SubsystemBase {
    
    private final DigitalInput beamBreakLeft = new DigitalInput(Constants.climberConstants.beamBreakLeftPort);
    private final CANSparkMax m_climbLeft = new CANSparkMax(Constants.climberConstants.climbLeftMotorCanID,MotorType.kBrushless);

    public ClimbLeft(){
        m_climbLeft.setIdleMode(IdleMode.kBrake);
        m_climbLeft.setSmartCurrentLimit(Constants.climberConstants.currentLimit);
        m_climbLeft.burnFlash();
    }
    private void raiseLeft(){
        while(beamBreakLeft.get()){
            leftRaiseCommand();
        }
    }

    private void climbLeft(){
        while(beamBreakLeft.get()){
            leftClimbCommand();
        }
    }

    public Command commandRaiseLeft(){
        return runOnce(() -> raiseLeft());
    }
    public Command commandClimbLeft(){
        return runOnce(() -> climbLeft());
    }

    public Command leftRaiseCommand(){
        return this.startEnd(() -> m_climbLeft.set(Constants.climberConstants.climbRaiseSpeed), () -> m_climbLeft.stopMotor());
    }
    public Command leftClimbCommand(){
        return this.startEnd(() -> m_climbLeft.set(Constants.climberConstants.climbLowerSpeed), () -> m_climbLeft.stopMotor());
    }
    
    public Command stopClimbLeftCommand(){
        return this.runOnce(() -> m_climbLeft.stopMotor());
    }
      
}
