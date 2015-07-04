package gt.demo;

import java.math.BigDecimal;
import java.util.List;

import gt.demo.modelo.StProducto;
import gt.demo.srv.GeneralSrv;
import gt.demo.srv.impl.GeneralSrvImpl;

import org.hibernate.Session;


import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.transform.Transformers;
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hola mundo");
		Demo d=new Demo();
	}
	
	public Demo(){
		GeneralSrv g=new GeneralSrvImpl();
		System.out.println("************* "+g.getListadoProductos().size());
		updateProducto(1);
		deleteProducto();
		insertarProducto();
		listProductos();
		System.out.println("fin");
		MostrarDatos();
	}
	
	public int insertarProducto(){
	try{	
		// Se obtiene la sesion
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        // Se instancia la clase Flight y se rellenan sus datos
		StProducto prod=new StProducto();
		prod.setIdProducto(5);
		prod.setProducto("Nuevo Producto E");
		prod.setDescripcion("Prueba del producto E, esto es una prueba.");
		prod.setPathProducto("resources/img/1.jpg");
		prod.setPrecio(new BigDecimal(350.00));
		prod.setExistencias(110);
		//prod.setStClienteProductos(null);

        // Se salva en base de datos
        s.save(prod);
        s.getTransaction().commit();
        return prod.getIdProducto();
	}catch(Exception e){
		System.out.println("Error "+ e.getMessage());
		e.printStackTrace();
	}
        return 0;
	}

	public int deleteProducto(){
		try{	
			// Se obtiene la sesion
	        Session s = HibernateUtil.getSessionFactory().openSession();
	        s.beginTransaction();
			StProducto prod=new StProducto();
			prod.setIdProducto(5);
	        s.delete(prod);
	        s.getTransaction().commit();
	        return prod.getIdProducto();
		}catch(Exception e){
			System.out.println("Error "+ e.getMessage());
			e.printStackTrace();
		}
	        return 0;
		}
	
	 private void updateProducto(int id) {
	        Session s = HibernateUtil.getSessionFactory().openSession();
	        s.beginTransaction();

	        StProducto vuelo = (StProducto) s.load(StProducto.class, id);
	        vuelo.setDescripcion("Nombre cambiado");

	        s.getTransaction().commit();
	 }
	
	
	private void listProductos() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

        List<StProducto> vuelos = s.createQuery("from StProducto").list();
        for (StProducto vuelo : vuelos)
            System.out.println(vuelo.getIdProducto()+" "+vuelo.getProducto()+" "+vuelo.getDescripcion());

        s.getTransaction().commit();
    }
	public void MostrarDatos(){
		Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();

		System.out.println(findByNamedQuery(s,"findprueba"));
		s.getTransaction().commit();
	}
	

	public <T> List<T> findByNamedQuery(Session session, String queryName, Object[] values, Class transformer) {
	        Query query = session.getNamedQuery(queryName);
	        if(values != null) {
	            for(int i = 0; i < values.length; i++) {
	                query.setParameter(i, values[i]);
	            }
	        }
	        final List<T> entidades = query.setResultTransformer(Transformers.aliasToBean(transformer)).list();
	        return entidades;
	   }
	  
	
	    public <T> List<T> findByNamedQuery(Session session, String queryName, Object... values) {
	        Query query = session.getNamedQuery(queryName);
	        if(values != null) {
	            for(int i = 0; i < values.length; i++) {
	                query.setParameter(i, values[i]);
	            }
	        }
	        final List<T> entidades = query.list();
	        return entidades;
	    }
}
