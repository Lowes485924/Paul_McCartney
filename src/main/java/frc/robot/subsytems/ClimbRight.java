package frc.robot.subsytems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbRight extends SubsystemBase {

    private final DigitalInput beamBreakRight = new DigitalInput(Constants.climberConstants.beamBrakeRightPort);
    private final CANSparkMax m_climbRight = new CANSparkMax(Constants.climberConstants.climbRightMotorCanID,MotorType.kBrushless);

    public ClimbRight(){
        m_climbRight.setIdleMode(IdleMode.kBrake);
        m_climbRight.setSmartCurrentLimit(Constants.climberConstants.currentLimit);
        m_climbRight.burnFlash();
    }

    private void raiseRight(){
        while(beamBreakRight.get()){
            rightRaiseCommand();
        }
    }

    private void climbRight(){
        while(beamBreakRight.get()){
            rightClimbCommand();
        }
    }


    public Command commandRaiseRight(){
        return runOnce(() -> raiseRight());
    }

    public Command commandClimbRight(){
        return runOnce(() -> climbRight());
    }


    public Command rightRaiseCommand(){
        return this.startEnd(() -> m_climbRight.set(Constants.climberConstants.climbRaiseSpeed), () -> m_climbRight.stopMotor());
    }

    public Command rightClimbCommand(){
        return this.startEnd(() -> m_climbRight.set(Constants.climberConstants.climbLowerSpeed), () -> m_climbRight.stopMotor());
    }

    public Command stopClimbRightCommand(){
        return this.runOnce(() -> m_climbRight.stopMotor());
    }    
}
