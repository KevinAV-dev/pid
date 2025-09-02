package com.app.springboot.formulario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitucionEducativaServicio {

    @Autowired
    private InstitucionEducativaRepository institucionEducativaRepository;

    public List<InstitucionEducativa> getInstitucionesEducativas() {
        return institucionEducativaRepository.findAll();
    }
}
