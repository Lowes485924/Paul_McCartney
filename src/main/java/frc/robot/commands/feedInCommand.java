package frc.robot.commands  ;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.RobotContainer;
import frc.robot.subsytems.Feeder;


public class feedInCommand extends Command{
    private Feeder feed;
    private double percent;
    public feedInCommand(Feeder feed, double percent){
        this.feed = feed;
        this.percent = percent;
  
      
        addRequirements(feed);

    }

    @Override
    public void initialize(){
    }
    
    @Override 
    public void execute(){
        feed.feed(percent);
    }
    
    @Override
    public void end(boolean interrupted){
        feed.feed(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}