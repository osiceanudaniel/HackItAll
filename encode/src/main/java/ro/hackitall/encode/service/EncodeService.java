package ro.hackitall.encode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hackitall.encode.dal.EncodeDal;

/**
 * Created by Andrei-Daniel Ene (andreidaniel.ene@bcr.ro) on 3/22/2018.
 */
@Service
public class EncodeService {
    @Autowired
    EncodeDal encodeDal;
}
