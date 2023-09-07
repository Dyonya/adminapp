package com.example.demo.controller;

import com.example.demo.entity.NotificationRequest;
import com.example.demo.repository.Computer;
import com.example.demo.repository.ComputerRepository;
import com.example.demo.service.EmployeeAbbreviationGenerator;
import com.example.demo.service.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computers")
public class ComputerController {

    @Autowired
    private ComputerRepository computerRepository;
    private EmployeeAbbreviationGenerator employeeAbbreviationGenerator;
    private NotificationRequest notificationRequest;
    private NotificationSender notificationSender;

    @GetMapping
    public Iterable<Computer> getAllComputers() {
        return computerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Computer getComputerById(@PathVariable long id) {
        return computerRepository.findById(id).orElse(null);
    }

    @GetMapping("/byComputerName/{computerName}")
    public Computer getComputersByComputerName(@PathVariable String computerName) {
        return computerRepository.findByComputerName(computerName);
    }

    @GetMapping("/byEmployeeName/{employeeAbbreviation}")
    public List<Computer> getComputersByEmployeeAbbreviation(@PathVariable String employeeAbbreviation) {
        return computerRepository.findByEmployeeAbbreviation(employeeAbbreviation);
    }

    @PostMapping
    public Computer createComputer(@RequestBody Computer computer) {
        computer.setEmployeeAbbreviation(employeeAbbreviationGenerator.generateAbbreviation(computer.getEmployeeAbbreviation()));

        checkMaxOfComputersForAnEmployee(computer.getEmployeeAbbreviation());

        return computerRepository.save(computer);
    }

    @PutMapping("/{id}")
    public Computer updateComputer(@PathVariable long id, @RequestBody Computer updatedComputer) {
        checkMaxOfComputersForAnEmployee(updatedComputer.getEmployeeAbbreviation());

        Computer computer = computerRepository.findById(id).orElse(null);

        if (computer != null) {
            computer.setComputerName(updatedComputer.getComputerName());
            computer.setEmployeeAbbreviation(updatedComputer.getEmployeeAbbreviation());
            computer.setMacAddress(updatedComputer.getMacAddress());
            computer.setIpAddress(updatedComputer.getIpAddress());
            computer.setDescription(updatedComputer.getDescription());
            return computerRepository.save(computer);
        }

        return null;
    }

    @PutMapping("assignComputer/{employeeAbbreviation}")
    public Computer AssignComputerToEmployee(@PathVariable String employeeAbbreviation, @RequestBody Computer computer) {
        checkMaxOfComputersForAnEmployee(employeeAbbreviation);

        if (computer != null) {
            computer.setEmployeeAbbreviation(employeeAbbreviation);
            return computerRepository.save(computer);
        }

        return null;
    }

    @PutMapping("unAssignComputer/{id}")
    public Computer AssignComputerToEmployee(@PathVariable String computerName) {

        Computer computer = computerRepository.findByComputerName(computerName);

        if (computer != null) {
            computer.setEmployeeAbbreviation("");
            return computerRepository.save(computer);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteComputer(@PathVariable long id) {
        computerRepository.deleteById(id);
    }

    private void checkMaxOfComputersForAnEmployee(String employeeAbbreviation) {
        List<Computer> computerList = computerRepository.findByEmployeeAbbreviation(employeeAbbreviation);
        if (computerList.size() > 3) {
            notificationSender.sendNotification(employeeAbbreviation);
        }
    }
}


