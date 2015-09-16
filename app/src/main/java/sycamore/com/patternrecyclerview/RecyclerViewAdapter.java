package sycamore.com.patternrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Класс адаптера наследуется от RecyclerView.Adapter с указанием класса, который будет хранить
 * ссылки на виджеты элемента списка, т.е. класса, имплементирующего ViewHolder. В нашем случае
 * класс объявлен внутри класса адаптера.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Record> records;
    private static final String TAG = "RecyclerViewAdapter";

    public RecyclerViewAdapter(List<Record> records) {
        this.records = records;
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Record record = records.get(i);
        int iconResourceId = 0;
        switch (record.getType()) {
            case GREEN:
                iconResourceId = R.drawable.green_circle;
                break;
            case RED:
                iconResourceId = R.drawable.red_circle;
                break;
            case YELLOW:
                iconResourceId = R.drawable.yellow_circle;
                break;
        }
        viewHolder.icon.setImageResource(iconResourceId);
        viewHolder.name.setText(record.getName());
        viewHolder.deleteButtonListener.setRecord(record);
        viewHolder.copyButtonListener.setRecord(record);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    private void copy(Record record) {
        int position = records.indexOf(record);
        Record copy = record.copy();
        records.add(position + 1, copy);
        notifyItemInserted(position + 1);
    }

    private void delete(Record record) {
        int position = records.indexOf(record);
        records.remove(position);
        notifyItemRemoved(position);
    }

//    private void edit(Record record) {
//        int position = records.indexOf(record);
//        records.get(position).setName("edited");
//        notifyItemChanged(position);
//    }

    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView icon;
        private Button deleteButton;
        private Button copyButton;
        private DeleteButtonListener deleteButtonListener;
        private CopyButtonListener copyButtonListener;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.recyclerViewItemName);
            icon = (ImageView) itemView.findViewById(R.id.recyclerViewItemIcon);
            deleteButton = (Button) itemView.findViewById(R.id.recyclerViewItemDeleteButton);
            copyButton = (Button) itemView.findViewById(R.id.recyclerViewItemCopyButton);
            deleteButtonListener = new DeleteButtonListener();
            copyButtonListener = new CopyButtonListener();
            deleteButton.setOnClickListener(deleteButtonListener);
            copyButton.setOnClickListener(copyButtonListener);

        }
    }

    private class CopyButtonListener implements View.OnClickListener {
        private Record record;

        @Override
        public void onClick(View v) {
            copy(record);
        }

        public void setRecord(Record record) {
            this.record = record;
        }
    }

    private class DeleteButtonListener implements View.OnClickListener {
        private Record record;

        @Override
        public void onClick(View v) {
            delete(record);
        }

        public void setRecord(Record record) {
            this.record = record;
        }
    }

}
