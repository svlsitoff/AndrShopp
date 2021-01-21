package my.dev.shopapp.shop;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import my.dev.shopapp.R;

/*This class implements a custom
 dialog for adding products
  to the list displayed in Activity*/
public class AddProductDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_add_product, null))
                .setPositiveButton( "Add new Product", ( DialogInterface.OnClickListener )getActivity( ) )
                .setNegativeButton( "Cancel", ( DialogInterface.OnClickListener )getActivity( ));
        return builder.create();
    }
}
