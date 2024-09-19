package ua.com.alevel.final_project;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class A {
    @Transactional
    public void aa(){
        bb();
    }
    @Transactional(propagation = Propagation.NEVER)
    protected void bb(){

    }
}
