package restaurante.agentes.cliente;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import restaurante.Const;
import restaurante.cardapio.Cardapio;
import restaurante.cardapio.Pedido;

public class FazerPedido extends Behaviour 
{
	private AID garcom;
	
	private Pedido pedido;
	
	private final int FAZER_PEDIDO = 0;
	private final int RECEBER_PEDIDO = 1;
	
	private int estado = FAZER_PEDIDO;
	
	public FazerPedido(Agent a, AID garcom) 
	{
		super(a);
		
		this.garcom = garcom;
		
		pedido = new Pedido(a.getAID(), Cardapio.selecionarItem());
		System.out.println();
	}

	@Override
	public void action() 
	{
		switch(estado)
		{
		case FAZER_PEDIDO:
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.addReceiver(garcom);
			msg.setConversationId(Const.PEDIDO);
			
			try {
				msg.setContentObject(pedido);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(myAgent.getLocalName() + ": Quero " + pedido.getItem().getNome() + ", por favor.");
			
			myAgent.send(msg);
			
			estado = RECEBER_PEDIDO;
			break;
			
		case RECEBER_PEDIDO:
			ACLMessage pedido = myAgent.receive();
			
			if(pedido != null)
			{
				
			}
			else
				block();
			
			break;
		}
		
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}