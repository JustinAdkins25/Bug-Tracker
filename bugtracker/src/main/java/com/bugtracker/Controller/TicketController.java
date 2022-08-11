//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

        package com.bugtracker.Controller;

import com.bugtracker.Model.Ticket;
import com.bugtracker.Model.User;
import com.bugtracker.Repository.TicketRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bugtracker.ResourceNotFoundException.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    public TicketController() {
    }

    @GetMapping({"/tickets/"})
    public List<Ticket> getTickets() {
        return this.ticketRepository.findAll();
    }

    @PostMapping({"/create-ticket/"})
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return (Ticket)this.ticketRepository.save(ticket);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket does not exist with id : " + id));
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping({"/delete-ticket/{id}"})
    public ResponseEntity<Map<String, Boolean> > deleteTicket(@PathVariable Long id) {
      Ticket ticket = this.ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + id));
        this.ticketRepository.deleteById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }

    @PutMapping({"/update-ticket/{id}"})
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticketInput, @PathVariable Long id) {
        Ticket ticket = (Ticket)this.ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + id));
        ticket.setId(ticketInput.getId());
        ticket.setDescription(ticketInput.getDescription());
        ticket.setPriority(ticketInput.getPriority());
        ticket.setTimestamp(ticketInput.getTimestamp());
        Ticket updateTicket = ticketRepository.save(ticket);
        return ResponseEntity.ok(updateTicket);
    }
}
