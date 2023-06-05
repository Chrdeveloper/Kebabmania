from django.http import JsonResponse

from kebabmania.models import Ciudad

 #Recoge todas las ciudades
def allcity(request):
    if request.method != "GET":
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    all_rows = Ciudad.objects.all()

    json_response = []

    for row in all_rows:
        json_response.append(row.to_json())

    return JsonResponse(json_response, safe=False)