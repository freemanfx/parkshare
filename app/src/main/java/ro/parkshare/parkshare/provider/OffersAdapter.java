package ro.parkshare.parkshare.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ro.parkshare.parkshare.R;
import ro.parkshare.parkshare.helper.DateHelper;
import ro.parkshare.parkshare.service.Offer;
import ro.parkshare.parkshare.service.Validity;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    private final Context context;
    private List<Offer> offerList;

    OffersAdapter(Context context, List<Offer> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_card_item, parent, false);
        return new ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = offerList.get(position);
        holder.bind(offer);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public void replaceData(List<Offer> offers) {
        offerList = offers;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView from;
        TextView to;
        private Context context;

        ViewHolder(View itemView, Context context) {
            super(itemView);
            from = itemView.findViewById(R.id.from_text);
            to = itemView.findViewById(R.id.to_text);
            this.context = context;
        }

        void bind(Offer offer) {
            Validity validity = offer.getValidity();
            DateHelper dateHelper = new DateHelper(context);
            from.setText(dateHelper.shortFormat(validity.getStart()));
            to.setText(dateHelper.shortFormat(validity.getEnd()));
        }
    }
}
