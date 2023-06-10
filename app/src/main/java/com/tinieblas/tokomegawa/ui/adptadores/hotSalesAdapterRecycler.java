package com.tinieblas.tokomegawa.adptadores;

//import com.tinieblas.tokomegawa.databinding.RowItemBinding;

/*
public class hotSalesAdapterRecycler extends RecyclerView.Adapter<hotSalesAdapterRecycler.ViewHolder> {
    //private final List<ModelohotSales> modelohotSales;

    private final Context context;
    private final LayoutInflater layoutInflater;
    //private final RecyclerViewInterface recyclerViewInterface;

    public hotSalesAdapterRecycler(List<ProductosItem> modelohotSales, Context context){

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return modelohotSales.size();
    }

    @NonNull
    @Override
    public hotSalesAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardProductosHotSalesBinding rowItemBinding = CardProductosHotSalesBinding.inflate(layoutInflater, parent, false);
        //View view = layoutInflater.inflate(R.layout.row_item, null);
        return new ViewHolder(rowItemBinding);
        //return new hotSalesAdapterRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hotSalesAdapterRecycler.ViewHolder holder, int position) {
        holder.bindData(modelohotSales.get(position));

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardProductosHotSalesBinding cardProductosHotSalesBinding;
        /*TextView Titulo;
        TextView Descripcion;
        ImageView Imagen1;
        //String Imagen2 = ListProducts.get(i).getImagen2();
        //String Imagen3 = ListProducts.get(i).getImagen3();
        //String Imagen4 = ListProducts.get(i).getImagen4();
        //float descuento = ListProducts.get(i).getDescuento();
        TextView Precio;
        String delivery = ListProducts.get(i).getDelivery();*/
  /*      public ViewHolder(@NonNull CardProductosHotSalesBinding rowItemBinding) {

            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
            /*Titulo = itemView.findViewById(R.id.textTituloHotSales);
            Descripcion = itemView.findViewById(R.id.textDescripcionHotSales);
            Imagen1 = itemView.findViewById(R.id.imageImagenHotSales);
            Precio = itemView.findViewById(R.id.textPrecioHotSales);*/
   /*     }

        public void bindData(final ModelohotSales item) {
            cardProductosHotSalesBinding.textTituloHotSales.setText(item.getTitulo());
            cardProductosHotSalesBinding.textDescripcionHotSales.setText(item.getDescripcion());
            cardProductosHotSalesBinding.textPrecioHotSales.setText(String.format("S/%s", (item.getPrecio())));

            RandomColor randomColor = new RandomColor();
            cardProductosHotSalesBinding.cardImagenHotSales.setCardBackgroundColor(randomColor.getColor());

            Glide.with(itemView).load(item.getImagen1())
                    .placeholder(R.drawable.frame_headphone)
                    .into(rowItemBinding.imageImagenHotSales);
        }
    }
}*/