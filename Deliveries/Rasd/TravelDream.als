module traveldream

sig NostraStringa{}

sig PosInt{
val:Int
}{val>0}

sig Data{
d_id: one PosInt
}

sig Password{
password:one NostraStringa
}

sig Username{
username:one NostraStringa
}

sig Credenziale{
username: one Username,
password: one Password
}

sig CodiceFiscale{
cf:one NostraStringa
}

sig Anagrafica {}

abstract sig Persona{
anagrafica:one Anagrafica,
cf: one CodiceFiscale,
credenziali:lone Credenziale
}

sig Citta{
città:one NostraStringa
}

sig Utente_non_Registrato extends Persona{}{#credenziali=0}

sig Amministratore extends Persona{}{#credenziali=1}

sig Utente_Registrato extends Persona{
u_id: one PosInt,
u_prenotazioni: set Prenotazione
}{#credenziali=1}

sig Dipendente extends Utente_Registrato{
pacchetti: set Pacchetto
}

sig Aereo{
a_id:  one PosInt,
posti:one PosInt,
citta_partenza: one Citta,
citta_arrivo: one Citta,
t_costo: one PosInt,
t_data: one Data

}

sig Camera{
cam_id:one PosInt,
cam_posti: one PosInt,
cam_prezzo: one PosInt,
cam_checkin: one Data,
cam_checkout: one Data
}

sig Hotel{
h_id: one PosInt,
h_citta: one Citta,
camere: set Camera
}

sig Escursione{
e_id: one PosInt,
e_luogo:one Citta,
e_prezzo:one PosInt,
e_partenza:one Data
}

sig Pagamento{
pag_id:one PosInt,
pag_data: one Data,
importo:one PosInt
}

sig Prenotazione{
pre_id: one PosInt,
pre_pagamento: one Pagamento,
id_viaggio:one Viaggio,
pre_data:one Data
}

sig Viaggio{
v_id:one PosInt,
v_hotel: lone Hotel,
v_aerei: set Aereo,
v_autore: lone Utente_Registrato,
v_escursione: set Escursione
}//{#v_aerei<=2}

sig Pacchetto extends Viaggio{ 
p_hotel: some Hotel,
p_aereo_andata: some Aereo,
p_aereo_ritorno: some Aereo,
p_autore: one Dipendente,
p_escursione: some Escursione,
p_data_inizio:one Data,
p_data_fine:one Data,
destinazione: one Citta
}{#v_autore=0
	#v_hotel = 0
	#v_aerei= 0
	#v_escursione= 0
}

sig Condivisione{
condividente:one Utente_Registrato,
condiviso:some Persona,
pacchetto_condiviso:one Pacchetto
}
	
//:::::::::::::::::::::::::::::::::::::::VINCOLI DI UNICITÀ::::::::::::::::::::::::::::::::::::::::::::

// IL SISTEMA HA UN SOLO AMMINISTRATORE
fact oneAdmin{
#Amministratore=1
}

assert unAmministratore{
#Amministratore=1
}


check unAmministratore

// NON ESISTONO DUE UTENTI REGISTRATI CON LO STESSO IDENTIFICATIVO
fact utenteNonDuplicato{
all u:Utente_Registrato{no u2:Utente_Registrato | u!=u2 and u.u_id=u2.u_id}
}

assert utenteNonDuplicato{
all u1:Utente_Registrato | some u2:Utente_Registrato | u1.u_id=u2.u_id implies u1=u2
}

check utenteNonDuplicato

//GLI ID DELLE SIGNATURE DEVONO ESSERE UNICI
fact idDiversi{
all d:Data{no d1:Data | d!=d1 and d.d_id=d1.d_id}
all a:Aereo {no a1:Aereo | a!=a1 and a.a_id=a1.a_id}
all c:Camera{no c1:Camera | c!=c1 and c.cam_id=c1.cam_id}
all h:Hotel{no h1:Hotel | h!=h1 and h.h_id=h1.h_id}
all e:Escursione{no e1:Escursione | e!=e1 and e.e_id=e1.e_id}
all pag:Pagamento{no pag1:Pagamento | pag!=pag1 and pag.pag_id=pag1.pag_id}
all pre:Prenotazione{no pre1:Prenotazione | pre!=pre1 and pre.pre_id=pre1.pre_id}
all v:Viaggio{no v1:Viaggio | v!=v1 and v.v_id=v1.v_id}
}

assert idDiversi{
all d:Data{some d1:Data | d.d_id=d1.d_id implies d=d1}
all a:Aereo {some a1:Aereo |a.a_id=a1.a_id implies a=a1}
all c:Camera{some c1:Camera |c.cam_id=c1.cam_id implies c=c1}
all h:Hotel{some h1:Hotel | h.h_id=h1.h_id implies h=h1}
all e:Escursione{some e1:Escursione | e.e_id=e1.e_id implies e=e1}
all pag:Pagamento{some pag1:Pagamento |pag.pag_id=pag1.pag_id implies pag=pag1}
all pre:Prenotazione{some pre1:Prenotazione |  pre.pre_id=pre1.pre_id implies pre=pre1}
all v:Viaggio{some v1:Viaggio | v.v_id=v1.v_id implies v=v1}
}

check idDiversi


//LO USERNAME È UNICO PER OGNI UTENTE. DA QUI DERIVA L'UNICITÀ DELLE CREDENZIALI

fact usernameUtenti{
all p:Persona{no p1:Persona| p!=p1  and p.credenziali.username = p1.credenziali.username}
}

assert usernameUnico{
all p:Persona{some p1:Persona | p1.credenziali.username=p.credenziali.username implies p=p1}
}

check usernameUnico

//UNA PRENOTAZIONE È RIFERITA AD UN SOLO VIAGGIO
fact prenotazioneUnViaggio{
all v:Viaggio{ no p1,p2:Prenotazione | p1!=p2 and v in p1.id_viaggio and v in p2.id_viaggio}
}

assert unaPrenotazioneUnViaggio{
all p:Prenotazione,v1:Viaggio,v2:Viaggio{v1 in p.id_viaggio and v2 in p.id_viaggio implies v1=v2}
}

check unaPrenotazioneUnViaggio

//IL CODICE FISCALE È RIFERITO AD UNA SOLA PERSONA
fact diversoCF{
all p1:Persona {no p2:Persona | p1!=p2 and p1.cf = p2.cf}
}

assert unaPersonaUnCf{
all p1:Persona{some p2:Persona | p1.cf=p2.cf implies p1=p2}
}

check unaPersonaUnCf

//UN PACCHETTO HA UN SOLO DIPENDENTE COME AUTORE
fact unSoloAutorePacchetto{
all p:Pacchetto{no d1,d2:Dipendente | d1!=d2 and p in d1.pacchetti and p in d2.pacchetti}
}

assert unSoloAutorePacchetto{
no p:Pacchetto{some d1,d2:Dipendente | d1!=d2 and p in d1.pacchetti and p in d2.pacchetti}
}

check unSoloAutorePacchetto

fact seAutore{
all p:Pacchetto{all d:Dipendente | p in d.pacchetti iff p.p_autore=d}
}

assert seAutore{
no p:Pacchetto{some d:Dipendente | p.p_autore=d and p  not in d.pacchetti}
}

check seAutore



//::::::::::::::::::::::::::::::::::::::::::::::::::::::VINCOLI SULL'ESISTENZA DELLE ENTITÀ
//UNA CAMERA ESISTE SOLO DENTRO UN ALBERGO
fact cameraInAlbergo{
 all c: Camera {some h: Hotel | c in h.camere}
}

assert cameraInAlbergo{
no c:Camera{no h:Hotel|c in h.camere}
}

check cameraInAlbergo

//UN PAGAMENTO ESISTE SOLO IN RELAZIONE AD UNA PRENOTAZIONE
fact pagamentoInPrenotazione{
all p:Pagamento{some pre:Prenotazione | p in pre.pre_pagamento}
}

assert pagamentoSePrenotazione{
no p:Pagamento{no pre:Prenotazione | p in pre.pre_pagamento}
}

check pagamentoSePrenotazione

//UN CODICE FISCALE NON ESISTE SE NON ESISTE LA PERSONA

fact noPersonaNoCF{
all c:CodiceFiscale{one p:Persona | c in p.cf}
}

assert noPersonanoCf{
no c:CodiceFiscale{no p:Persona | c in p.cf}
}

check noPersonanoCf

//OGNI PASSWORD PRESENTE NEL SISTEMA DEVE APPARTENERE AD UNA CREDENZIALE

fact passwordCredenziale{
all p:Password{some c:Credenziale | p in c.password}
}

assert passwordInCredenziale{
no p:Password {no c:Credenziale | p in c.password}
}

check passwordInCredenziale

//USERNAME ESISTE SE ESISTE LA CREDENZIALE
fact usernameCredenziale{
all u:Username{some c:Credenziale | u in c.username}
}

assert usernameInCredenziale{
no u:Username{no c:Credenziale | u in c.username}
}

check usernameInCredenziale

//UNA CREDENZIALE ESISTE SOLO SE ESISTE L'UTENTE ASSOCIATO
fact credenzialeSePersona{
all c:Credenziale{some p:Persona| c in p.credenziali} 
}

assert credendialeInPersona{
no c:Credenziale{no p:Persona | c in p.credenziali}
}

check credendialeInPersona

//A CITTÀ DI DECOLLO E DI ATTERRAGGIO PER UN AEREO SONO DIVERSE
fact aereoHaVolato{
all a:Aereo | a.citta_partenza!=a.citta_arrivo
}

assert aereoHaVolato{
all c:Citta{all a:Aereo | c in a.citta_partenza implies c not in a.citta_arrivo}
}

check aereoHaVolato

//UNA PRENOTAZIONE PUÒ ESSERE EFFETTUATA DA UN SOLO UTENTE
fact unaPrenotazioneUnUtente{
all p:Prenotazione | one u:Utente_Registrato | p in u.u_prenotazioni
}

assert unaPrenotazioneUnUtente{
no p:Prenotazione{some u1,u2:Utente_Registrato | p in u1.u_prenotazioni and p in u2.u_prenotazioni and u1!=u2}
}

check unaPrenotazioneUnUtente

//UN VIAGGIO CHE NON SIA UN PACCHETTO DEVE AVERE UN UTENTE COME AUTORE
fact viaggioAutore{
all v:Viaggio{all p:Pacchetto | v.v_id!=p.v_id implies #v.v_autore=1}
}

assert viaggioAutore{
no v:Viaggio{some p:Pacchetto | v.v_id=p.v_id and #v.v_autore=1}
}

check viaggioAutore

//UNA VIAGGIO DEVE CONTENERE ALMENO UNO TRA AEREO,HOTEL ED ESCURSIONE
fact viaggioMinimo{
all v:Viaggio{#v.v_autore=1 implies (#v.v_aerei + #v.v_hotel + #v.v_escursione)>=1}
}

assert viaggioMinimo{
no v:Viaggio | #v.v_autore=1 and (#v.v_aerei + #v.v_hotel + #v.v_escursione)=0
}

check viaggioMinimo

//UN PACCHETTO DEVO CONTENERE HOTEL E ESCURSIONI SUCCESSIVE ALLA DATA DI DECOLLO
fact coerenzaDataPacchettoHotel{
all p:Pacchetto{ all h:p.p_hotel{all c:h.camere| 
								(p.p_aereo_andata.t_data.d_id.val <= c.cam_checkin.d_id.val and 
								p.p_aereo_ritorno.t_data.d_id.val >= c.cam_checkout.d_id.val)}}
}

fact coerenzaDataPacchettoEscursione{
all p:Pacchetto{all e:Escursione | e in p.p_escursione implies
							 ( e.e_partenza.d_id.val>=p.p_aereo_andata.t_data.d_id.val and
										e.e_partenza.d_id.val<= p.p_aereo_ritorno.t_data.d_id.val)}
}

fact coerenzaDataPacchettoVoloRitorno{
all p:Pacchetto{all a:Aereo | a in p.p_aereo_ritorno implies a.t_data .d_id.val>p.p_aereo_andata.t_data.d_id.val}
}

fact coerenzaDataPacchettoVoloAndata{
all p:Pacchetto{all a:Aereo | a in p.p_aereo_andata implies a.t_data .d_id.val<p.p_aereo_ritorno.t_data.d_id.val}
}

assert coerenzaDataPacchettoHotel{
no p:Pacchetto{some h:p.p_hotel{some c:h.camere |
								 (p.p_aereo_andata.t_data.d_id.val >c.cam_checkin.d_id.val or 
									p.p_aereo_ritorno.t_data.d_id.val <c.cam_checkout.d_id.val)}}
}

assert coerenzaDataPacchettoEscursione{
no p:Pacchetto{some e:Escursione | e in p.p_escursione and
							 ( e.e_partenza.d_id.val<p.p_aereo_andata.t_data.d_id.val or
										e.e_partenza.d_id.val> p.p_aereo_ritorno.t_data.d_id.val)}
}

assert coerenzaDataPacchettoVoloRitorno{
no p:Pacchetto{some a:Aereo | a in p.p_aereo_ritorno and a.t_data .d_id.val<=p.p_aereo_andata.t_data.d_id.val}
}

assert coerenzaDataPacchettoVoloAndata{
no p:Pacchetto{some a:Aereo | a in p.p_aereo_andata and a.t_data .d_id.val>=p.p_aereo_ritorno.t_data.d_id.val}
}

check coerenzaDataPacchettoHotel

check coerenzaDataPacchettoEscursione

check coerenzaDataPacchettoVoloRitorno

check coerenzaDataPacchettoVoloAndata


//UN PACCHETTO DEVE CONTENERE HOTEL E ESCURSIONI SINO ALLA DATA DI RITORNO DELL'AEREO
fact coerenzaDataPacchettoAtterraggio{
all p:Pacchetto{ no h:Hotel,e:Escursione,a:Aereo | 
								h in p.p_hotel and e in p.p_escursione and a in p.p_aereo_andata and
								p.p_aereo_ritorno.t_data.d_id.val < a.t_data .d_id.val and 
								p.p_aereo_ritorno.t_data.d_id.val< e.e_partenza.d_id.val and
								p.p_aereo_ritorno.t_data.d_id.val< h.camere.cam_checkout.d_id.val}
}

assert vacanzaFinoAtterraggio{
all p:Pacchetto{ some h:Hotel,e:Escursione,a:Aereo | 
								h in p.p_hotel and e in p.p_escursione and a in p.p_aereo_andata implies
								(p.p_aereo_ritorno.t_data.d_id.val >= a.t_data .d_id.val and 
								p.p_aereo_ritorno.t_data.d_id.val>= e.e_partenza.d_id.val and
								p.p_aereo_ritorno.t_data.d_id.val>= h.camere.cam_checkout.d_id.val)}
}


check vacanzaFinoAtterraggio

//IL PACCHETTO DEVE AVERE UNA DURATA MINIMA DI UN GIORNO
fact durataMinima{
all p:Pacchetto{all a:Aereo| a in p.p_aereo_ritorno implies {some a1:Aereo | a.t_data.d_id.val > a1.t_data.d_id.val and a1 in p.p_aereo_andata}}
}


assert DurataMinima{
no p:Pacchetto{some a:Aereo| a in p.p_aereo_ritorno and {all a1:Aereo | a.t_data.d_id.val <= a1.t_data.d_id.val and a1 in p.p_aereo_andata}}
}

check DurataMinima



//IL CHECKOUT DI UNA CAMERA DEVE ESSERE SUCCESSIVO AL CHECKIN
fact checkOutCheckIn{
all c:Camera | c.cam_checkin.d_id.val<= c.cam_checkout.d_id.val
}

assert checkoutDopoCheckin{
no c:Camera | c.cam_checkin.d_id.val> c.cam_checkout.d_id.val
}

check checkoutDopoCheckin


//LA DATA DI INIZIO VALIDITÀ DEL PACCHETTO È ANTECEDENTE A QUELLA DI FINE VALIDITÀ
fact dataInizioPrimadiFine{
all p:Pacchetto | p.p_data_inizio.d_id.val < p.p_data_fine.d_id.val
}

assert dataInizioPrimadiFine{
no p:Pacchetto | p.p_data_inizio.d_id.val >= p.p_data_fine.d_id.val
}

check dataInizioPrimadiFine

//IN UN PACCHETTO L'AEREO DI ANDATA È DIVERSO DA QUELLO DI RITORNO
fact aereoDiverso{
all p:Pacchetto{all a:Aereo | a in p.p_aereo_andata implies a not in p.p_aereo_ritorno}
}

assert aereoDiversoNelPacchetto{
no p:Pacchetto{some a1,a2:Aereo | a1 in p.p_aereo_andata and a2 in p.p_aereo_ritorno and a1=a2}
}

check aereoDiversoNelPacchetto

//LA PRENOTAZIONE DEVE ESSERE COMPRESA TRA L'INIZIO VALIDITÀ DEL PACCHETTO E LA FINE MENO LA DURATA DEL VIAGGIO
fact prenotazioneNelPeriodoValidità{
all p:Prenotazione{all pac:Pacchetto | pac in p.id_viaggio implies 
											(p.pre_data.d_id.val>= pac.p_data_inizio.d_id.val and
												p.pre_data.d_id.val<= pac.p_data_fine.d_id.val-(	pac.p_data_fine.d_id.val-pac.p_data_inizio.d_id.val))}												

}

assert prenotazioneNelPeriodoValidità{
no p:Prenotazione{some pac:Pacchetto | pac in p.id_viaggio and p.pre_data.d_id.val< pac.p_data_inizio.d_id.val and
																				p.pre_data.d_id.val> pac.p_data_fine.d_id.val-(	pac.p_data_fine.d_id.val-pac.p_data_inizio.d_id.val)}
}

check prenotazioneNelPeriodoValidità

//LA CITTÀ DI ATTERRAGGIO DEL VOLO DI ANDATA E LA STESSA DI DECOLLO DEL VOLO DI RITORNO
fact aereoParteDaDoveArriva{
all p:Pacchetto{all a1:Aereo | a1 in p.p_aereo_andata implies {some a2:Aereo | a2 in p.p_aereo_ritorno and a1.citta_arrivo=a2.citta_partenza}}
}

assert aereoParteDaDoveArriva{
no p:Pacchetto{some a1:Aereo | a1 in p.p_aereo_andata and {no a2:Aereo | a2 in p.p_aereo_ritorno and a1.citta_arrivo=a2.citta_partenza}}
}

check aereoParteDaDoveArriva

//LA CITTÀ DI DECOLLO DEL VOLO DI ANDATA È LA STESSA DI ATTERRAGGIO DEL VOLO DI RITORNO
fact cittaPartenzaDecollo{
all p:Pacchetto {all a1:Aereo| a1 in p.p_aereo_andata implies {some a2:Aereo | a2 in p.p_aereo_ritorno and a1.citta_partenza=a2.citta_arrivo}}
}

assert cittaPartenzaDecollo{
no p:Pacchetto{some a1:Aereo | a1 in p.p_aereo_andata and {all a2:Aereo | a2 in p.p_aereo_ritorno and a1.citta_partenza!=a2.citta_arrivo}}
}

check cittaPartenzaDecollo

//GLI AEREI DI ANDATA DEVONO AVERE LA CITTÀ DI ATTERRAGGIO IDENTICA ALLA DESTINAZIONE DEL PACCCHETTO
fact cittaDestinazioneAndata{
all p:Pacchetto{all a:Aereo | a in p.p_aereo_andata implies a.citta_arrivo =p.destinazione}
}
//GLI AEREI DI RITORNO DEVONO AVERE LA CITTÀ DI DECOLLO IDENTICA ALLA DESTINAZIONE DEL PACCHETTO
fact cittaPartenzaRitorno{
all p:Pacchetto{all a:Aereo | a in p.p_aereo_ritorno implies a.citta_partenza=p.destinazione}
}

assert cittaDestinazioneAndata{
no p:Pacchetto{some a:Aereo | a in p.p_aereo_andata and a.citta_arrivo !=p.destinazione}
}

assert cittaPartenzaRitorno{
no p:Pacchetto{some a:Aereo | a in p.p_aereo_ritorno and a.citta_partenza!=p.destinazione}
}

check cittaDestinazioneAndata

check cittaPartenzaRitorno

//TUTTI GLI HOTEL APPARTENENTI AD UN PACCHETTO DEVONO ESSERE SITUATI NELLA DESTINAZIONE DEL PACCHETTO
fact destinazionePacchettoHotel{
all p:Pacchetto{all h:Hotel | h in p.p_hotel implies p.destinazione = h.h_citta}
}

assert destinazionePacchettoHotel{
no p:Pacchetto{some h:Hotel | h in p.p_hotel and p.destinazione != h.h_citta}
}

check destinazionePacchettoHotel

//TUTTE LE ESCURSIONI APPARTENENTI AD UN PACCHETTO DEVONO SVOLGERSI NELLA DESTINAZIONE DEL PACCHETTO
fact destinazionePacchettoEscursione{
all p:Pacchetto{all e:Escursione | e in p.p_escursione implies p.destinazione = e.e_luogo}
}

assert destinazionePacchettoEscursione{
no p:Pacchetto{some e:Escursione | e in p.p_escursione and e.e_luogo!=p.destinazione}
}

check destinazionePacchettoEscursione

//L'ANAGRAFICA ESISTE SE ESISTE LA PERSONA CHE LA CONTIENE
fact anagraficaSePersona{
all a:Anagrafica{one p:Persona | a in p.anagrafica}
}

assert anagraficaSePersona{
no a:Anagrafica{no p:Persona | a in p.anagrafica}
}

check anagraficaSePersona

//SE UN PACCHETTO È PRENOTATO ALLORA CONTIENE
// 1 HOTEL, 1 VOLO DI ANDATA, 1 VOLO DI RITORNO, 1 O PIÙ ESCURSIONI
fact componentiPacchettoPrenotato{
all p:Pacchetto{all pre:Prenotazione | p in pre.id_viaggio implies (#p.p_hotel=1 and #p_aereo_andata=1 and
										#p_aereo_ritorno=1 and #p_escursione>=1)}
}

assert componentiPacchettoPrenotato{
no p:Pacchetto{some pre:Prenotazione | p in pre.id_viaggio and (#p.p_hotel!=1 or #p_aereo_andata!=1 or
										#p_aereo_ritorno!=1 or #p_escursione<1)}
}

check componentiPacchettoPrenotato

//IL PACCHETTO VIENE CONDIVISO DA UN UTENTE CON UN ALTRO
fact condivisionePacchetto{
all u:Utente_Registrato{some c:Condivisione | c.condividente = u implies {one p:u.u_prenotazioni | p.id_viaggio = c.pacchetto_condiviso}}
}

assert condivisionePacchetto{
no c:Condivisione{no u:Utente_Registrato | u=c.condividente and {no p:u.u_prenotazioni | c.pacchetto_condiviso = p.id_viaggio}}
}

check condivisionePacchetto

//UN UTENTE NON PUÒ CONDIVIDERE PACCHETTI CON SE STESSO
fact noCondivisioneRiflessiva{
all c:Condivisione {all u1,u2:Utente_Registrato| u1 in c.condividente and u2 in c.condiviso implies u1!=u2}
}

assert noCondivisioneRiflessiva{
no c:Condivisione | c.condividente=c.condiviso
}

check noCondivisioneRiflessiva

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

//PREDICATI 
pred aggiungiPacchetto[d,d1:Dipendente,p:Pacchetto]{
 p not in d.pacchetti => d1.pacchetti = d.pacchetti + p
}

run aggiungiPacchetto for 3

pred eliminaPacchetto[d,d1:Dipendente,p:Pacchetto]{
p in d.pacchetti => d1.pacchetti= d.pacchetti -p
}

run eliminaPacchetto for 3


pred showCoerenza{
#Dipendente=1
#Utente_Registrato=2
#Utente_non_Registrato=1
#Pacchetto=1
#Aereo=2
#Hotel=1
#Escursione=1
#Condivisione=2
#Prenotazione=1
}

run showCoerenza for 4


pred show{
#Dipendente=1
#Utente_Registrato=2
#Utente_non_Registrato=1
#Pacchetto=1
#Viaggio=2
#Prenotazione>0

}

run show for 4
